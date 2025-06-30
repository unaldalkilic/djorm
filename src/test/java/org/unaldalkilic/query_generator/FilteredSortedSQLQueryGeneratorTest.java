package org.unaldalkilic.query_generator;

import org.junit.jupiter.api.Test;
import org.unaldalkilic.command.SelectCommand;
import org.unaldalkilic.test_utils.MockCommandGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.unaldalkilic.command.command_filter.FilterableBasis.*;
import static org.unaldalkilic.command.command_sort.SortableBasis.asc;
import static org.unaldalkilic.command.command_sort.SortableBasis.desc;

public class FilteredSortedSQLQueryGeneratorTest extends SQLQueryGeneratorUtil{
    @Test
    public void testFilteredSQLQueryGenerator() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SelectCommand selectCommand = generator.select("students")
                .where(
                        and(
                                equal("name", "Ünal"),
                                lessThan("age", 30)
                        )
                );

        for (QueryGenerator dialect: dialects)
            assertEquals("SELECT * FROM students WHERE (name='Ünal' AND age<30) ;", dialect.select(selectCommand), dialect.getClass().getSimpleName());
    }

    @Test
    public void testComplexFilteredSQLQueryGenerator() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SelectCommand selectCommand = generator.select("students", "name", "age")
                .where(
                        or(
                                equal("name", "Ünal").not(),
                                and(
                                        lessThan("age", 30),
                                        lessThan("salary", 60000).not(),
                                        equal("surname", "Dalkılıç")
                                )
                        ).not()
                );

        for (QueryGenerator dialect: dialects)
            assertEquals("SELECT name,age FROM students WHERE NOT (NOT name='Ünal' OR (age<30 AND NOT salary<60000 AND surname='Dalkılıç')) ;", dialect.select(selectCommand), dialect.getClass().getSimpleName());
    }

    @Test
    public void testSortedSQLQueryGenerator() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SelectCommand selectCommand = generator.select("students", "name", "age")
                .sort(
                        "age",
                        desc("name", "surname"),
                        asc("salary")
                );

        for (QueryGenerator dialect: dialects)
            assertEquals("SELECT name,age FROM students ORDER BY surname DESC,name DESC,salary ASC,age ASC ;", dialect.select(selectCommand), dialect.getClass().getSimpleName());
    }

    @Test
    public void testFilteredSortedSQLQueryGenerator() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SelectCommand selectCommand = generator.select("students")
                .where(
                        and(
                                equal("name", "Ünal").not(),
                                lessThan("age", 30)
                        )
                ).sort(
                        "name",
                        desc("age")
                );

        for (QueryGenerator dialect: dialects)
            assertEquals("SELECT * FROM students WHERE (NOT name='Ünal' AND age<30) ORDER BY name ASC,age DESC ;", dialect.select(selectCommand), dialect.getClass().getSimpleName());
    }
}
