package org.unaldalkilic.connection;

import org.unaldalkilic.enums.DatabaseType;

public abstract class ConnectionTestUtil {
    public static final ConnectionTestCredential[] credentials = new ConnectionTestCredential[] {
            new ConnectionTestCredential("localhost", 3307, "root", "secretPass", "djorm_demo", DatabaseType.MYSQL)
    };
}

record ConnectionTestCredential(String host, int port, String user, String password, String database_name, DatabaseType type) {
}