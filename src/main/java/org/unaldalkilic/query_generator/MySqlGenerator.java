package org.unaldalkilic.query_generator;

public class MySqlGenerator extends SQLGenerator {
    private final static MySqlGenerator instance = new MySqlGenerator();;

    public static QueryGenerator instance() {
        return instance;
    }

    @Override
    protected String formatValue(Object value) {
        if (value instanceof Boolean)
            return ((Boolean) value) ? "1" : "0";

        return super.formatValue(value);
    }
}
