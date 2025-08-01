package org.unaldalkilic.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.unaldalkilic.command.Command;
import org.unaldalkilic.enums.DatabaseCategory;
import org.unaldalkilic.enums.DatabaseType;
import org.unaldalkilic.util.execution_result.ExecutionResult;
import org.unaldalkilic.util.execution_result.SQLExecutionResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnector extends Connector{
    private HikariDataSource dataSource;
    private HikariConfig hikariConfig;

    private SQLConnector(String host, int port, String user, String password, String database_name, DatabaseType type) {
        super(host, port, user, password, database_name, type);
        hikariConfig();
    }

    private void hikariConfig() {
        hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getJDBCUrl());
        hikariConfig.setUsername(getUser());
        hikariConfig.setPassword(getPassword());

        // Performance optimization
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        // Pool settings
        hikariConfig.setMinimumIdle(3);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setConnectionTimeout(3000);
        hikariConfig.setInitializationFailTimeout(-1); // For lazy initialize, do not create and test connections at object creation
    }

    public static SQLConnector createSQLConnector(String host, int port, String user, String password, String database_name, DatabaseType type) {
        if (host == null || user == null || password == null || database_name == null || type == null)
            throw new IllegalArgumentException("Arguments cannot be null");
        if (host.isBlank() || user.isBlank() || password.isBlank() || database_name.isBlank())
            throw new IllegalArgumentException("host, user, password or database_name arguments cannot be blank");
        if (port <= 0)
            throw new IllegalArgumentException("Given port number argument must be positive integer");
        if (type.get_category() != DatabaseCategory.SQL)
            throw new IllegalArgumentException("Given database type must be a SQL database");

        return new SQLConnector(host, port, user, password, database_name, type);
    }

    public String getJDBCUrl() {
        return "jdbc:"+get_type().toString()+"://"+getHost()+":"+getPort()+"/"+getDatabase_name();
    }

    public Connection getConnection() throws SQLException {
        if (!is_connected()) {
            if (!connect())
                throw new IllegalStateException("Cannot connect to database!");
        }
        return dataSource.getConnection();
    }

    @Override
    public boolean is_connected() {
        try {
            return dataSource != null &&
                    !dataSource.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean connect() {
        if (is_connected()) return true;

        dataSource = new HikariDataSource(hikariConfig);
        try (Connection conn = dataSource.getConnection()) {
            return conn.isValid(get_connection_timeout());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        if (!is_connected()) return true;

        try {
            dataSource.close();
            dataSource = null;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ExecutionResult execute(Command command){
        if (!is_connected()) {
            if (!connect())
                throw new IllegalStateException("Cannot connect to database!");
        }

        String command_query = query_generator_instance().auto(command); // Convert command object to a query string auto

        try (Statement statement = getConnection().createStatement()) {

            switch (command.getCommandType()) {
                case SELECT:
                    try (ResultSet resultSet = statement.executeQuery(command_query)) {
                        return SQLExecutionResult.from_result_set(resultSet, get_type());
                    }

                case INSERT:
                case UPDATE:
                case DELETE:
                    int affectedRows = statement.executeUpdate(command_query);
                    return SQLExecutionResult.from_affected_rows(affectedRows, get_type());

                default:
                    throw new IllegalStateException("Given command type is not supported");
            }

        } catch (SQLException e) {
            return SQLExecutionResult.from_exception(e, get_type());
        }
    }
}
