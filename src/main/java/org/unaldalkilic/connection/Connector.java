package org.unaldalkilic.connection;

import org.unaldalkilic.enums.DatabaseType;

public abstract class Connector {
    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String database_name;
    private final DatabaseType type;

    public Connector(String host, int port, String user, String password, String database_name, DatabaseType type) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database_name = database_name;
        this.type = type;
    }

    public Connector(Connector connector) {
        this(connector.getHost(), connector.getPort(), connector.getUser(), connector.getPassword(), connector.getDatabase_name(), connector.get_type());
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Connector temp = (Connector) obj;
        return host.equals(temp.getHost()) && port == temp.getPort() && user.equals(temp.getUser()) && password.equals(temp.getPassword()) && database_name.equals(temp.getDatabase_name()) && ();
    }

    @Override
    public String toString() {
        return host + ":" + port + "/" + database_name + "?user=" + user + "&password=" + password;
    }
}
