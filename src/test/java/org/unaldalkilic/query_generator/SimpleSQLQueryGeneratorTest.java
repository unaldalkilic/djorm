package org.unaldalkilic.query_generator;

import org.junit.jupiter.api.Test;
import org.unaldalkilic.command.Command;
import org.unaldalkilic.test_utils.MockCommandGenerator;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleSQLQueryGeneratorTest {
    @Test
    public void testSimpleSelectSQLQuery() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.select("students");
        String query = SQLQueryGenerator.select(command);

        assertEquals("SELECT * FROM students ;", query);
    }

    @Test
    public void testSimpleDeleteSQLCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.delete("students");
        String command_str = SQLQueryGenerator.delete(command);

        assertEquals("DELETE FROM students ;", command_str);
    }

    @Test
    public void testSimpleUpdateCommand() {
        Map<String, Object> update_data = new HashMap<>() {{
            put("name", "Ünal");
            put("age", 23);
        }};

        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.update("students", update_data);
        String command_str = SQLQueryGenerator.update(command);

        assertEquals("UPDATE students SET name='Ünal',age=23;", command_str);
    }
}
