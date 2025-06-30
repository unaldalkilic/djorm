package org.unaldalkilic.query_generator;

public abstract class SQLQueryGeneratorUtil {
    protected static final QueryGenerator[] dialects = new QueryGenerator[]{
            MySqlGenerator.instance(),
            PostgreSqlGenerator.instance()
    };
}
