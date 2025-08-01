package org.unaldalkilic.connection;

import org.unaldalkilic.enums.DatabaseType;
import org.unaldalkilic.query_generator.MySqlGenerator;
import org.unaldalkilic.query_generator.PostgreSqlGenerator;
import org.unaldalkilic.query_generator.QueryGenerator;

public abstract class Connector implements ConnectorInterface {
    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String database_name;
    private final DatabaseType type;
    private int connection_timeout = 10; // second based

    public Connector(String host, int port, String user, String password, String database_name, DatabaseType type) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database_name = database_name;
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase_name() {
        return database_name;
    }

    public DatabaseType get_type() {
        return type;
    }

    public int get_connection_timeout() { return connection_timeout; }

    public void set_connection_timeout(int timeout) {
        if (timeout <= 0) throw new IllegalArgumentException("timeout argument must be a positive integer");
        connection_timeout = timeout;
    }

    protected QueryGenerator query_generator_instance() {
        return switch (type) {
            case MYSQL -> MySqlGenerator.instance();
            case POSTGRESQL -> PostgreSqlGenerator.instance();
            default -> throw new IllegalStateException("Given database type has no query generator");
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Connector temp = (Connector) obj;
        return host.equals(temp.getHost()) && port == temp.getPort() && user.equals(temp.getUser()) && password.equals(temp.getPassword()) && database_name.equals(temp.getDatabase_name()) && (type == temp.get_type());
    }

    @Override
    public String toString() {
        return type.toString()+"@"+host + ":" + port + "/" + database_name + "?user=" + user + "&password=" + password;
    }
}
