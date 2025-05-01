package org.unaldalkilic.enums;

public enum DatabaseType {
    MYSQL,
    POSTGRESQL,
    MSQL,
    MONGO;

    public DatabaseCategory get_category() {
        return switch (this) {
            case MYSQL, POSTGRESQL, MSQL -> DatabaseCategory.SQL;
            case MONGO -> DatabaseCategory.MONGO;
            default -> throw new IllegalStateException("Invalid DatabaseType! It has no DatabaseCategory.");
        };
    }
}
