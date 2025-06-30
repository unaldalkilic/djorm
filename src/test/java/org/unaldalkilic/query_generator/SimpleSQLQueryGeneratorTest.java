package org.unaldalkilic.query_generator;

import org.junit.jupiter.api.Test;
import org.unaldalkilic.command.Command;
import org.unaldalkilic.test_utils.MockCommandGenerator;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleSQLQueryGeneratorTest extends SQLQueryGeneratorUtil {
    @Test
    public void testSimpleSelectSQLQuery() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.select("students");

        for (QueryGenerator dialect : dialects) {
            String query = dialect.select(command);
            assertEquals("SELECT * FROM students ;", query, "Dialect: " + dialect.getClass().getSimpleName());
        }
    }

    @Test
    public void testSimpleSelectWithSelectedFeaturesQuery() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.select("students", "name", "age");

        for (QueryGenerator dialect: dialects) {
            String query = dialect.select(command);
            assertEquals("SELECT name,age FROM students ;", query, "Dialect: " + dialect.getClass().getSimpleName());
        }
    }

    @Test
    public void testSimpleDeleteSQLCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.delete("students");

        for (QueryGenerator dialect : dialects) {
            String query = dialect.delete(command);
            assertEquals("DELETE FROM students ;", query, "Dialect: " + dialect.getClass().getSimpleName());
        }
    }

    @Test
    public void testSimpleUpdateCommand() {
        Map<String, Object> update_data = new HashMap<>() {{
            put("name", "Ünal");
            put("age", 23);
        }};

        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.update("students", update_data);

        for (QueryGenerator dialect : dialects) {
            String query = dialect.update(command);

            String expected = "UPDATE students SET name='Ünal',age=23;";
            assertEquals(expected, query, "Dialect: " + dialect.getClass().getSimpleName());
        }
    }

    @Test
    public void testSimpleInsertCommand() {
        Map<String, Object> insert_data = new HashMap<>() {{
            put("name", "Ünal");
            put("age", 23);
        }};

        MockCommandGenerator generator = new MockCommandGenerator();
        Command command = generator.insert("students", insert_data);

        for (QueryGenerator dialect : dialects) {
            String query = dialect.insert(command);

            String expected = "INSERT INTO students (name,age) VALUES ('Ünal',23);";
            assertEquals(expected, query, "Dialect: " + dialect.getClass().getSimpleName());
        }
    }

    @Test
    public void testAutoCommandDispatch() {
        Map<String, Object> update_data = new HashMap<>() {{
            put("name", "Ünal");
            put("age", 23);
        }};

        MockCommandGenerator generator = new MockCommandGenerator();
        Command updateCommand = generator.update("students", update_data);

        for (QueryGenerator dialect : dialects) {
            String query = dialect.auto(updateCommand);
            String expected = "UPDATE students SET name='Ünal',age=23;";
            assertEquals(expected, query, "Dialect: " + dialect.getClass().getSimpleName());
        }
    }

}
