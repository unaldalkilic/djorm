package org.unaldalkilic.query_generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.unaldalkilic.command.Command;
import org.unaldalkilic.test_utils.MockCommandGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLQueryGeneratorTest {
    @Test
    public void testSimpleSelectSQLQuery() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.select("students");
        String query = SQLQueryGenerator.select(command);

        assertEquals("SELECT * FROM students ;", query);
    }
}
