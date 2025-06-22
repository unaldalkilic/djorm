package org.unaldalkilic.query_generator;

public class PostgreSqlGenerator extends SQLGenerator {
    private static final PostgreSqlGenerator instance = new PostgreSqlGenerator();

    public static QueryGenerator instance() {
        return instance;
    }

    @Override
    protected String formatValue(Object value) {
        if (value instanceof Boolean)
            return ((Boolean) value) ? "TRUE" : "FALSE";

        return super.formatValue(value);
    }
}
