package org.unaldalkilic.command;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RawCommandTest {
    @Test
    public void testRawDeleteCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command delete_command = generator.delete("students");

        assertEquals("students", delete_command.getTarget());
        assertEquals(CommandType.DELETE, delete_command.getCommandType());
    }

    @Test
    public void testRawInsertCommand() {
        Map<String, Object> insert_data = new HashMap<>() {{
           put("name", "Ünal");
           put("age", 23);
        }};
        MockCommandGenerator generator = new MockCommandGenerator();
        InsertCommand insert_command = (InsertCommand) generator.insert("students", insert_data);

        assertEquals(CommandType.INSERT, insert_command.getCommandType());
        assertEquals("students", insert_command.getTarget());
        assertEquals(2, insert_command.getInsertData().size());
        assertTrue(insert_command.getInsertData().containsKey("name"));
        assertTrue(insert_command.getInsertData().containsKey("age"));
        assertEquals("Ünal", insert_command.getInsertData().get("name"));
        assertEquals(23, insert_command.getInsertData().get("age"));
    }

    @Test
    public void testRawSelectCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command select_command = generator.select("students");

        assertEquals(CommandType.SELECT, select_command.getCommandType());
        assertEquals("students", select_command.getTarget());
    }

    @Test
    public void testRawSelectWithSelectedFeatures() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SelectCommand select_command = generator.select("students", "name", "age");

        assertEquals(2, select_command.getSelected_features().size());
        assertEquals("name", select_command.getSelected_features().getFirst());
        assertEquals("age", select_command.getSelected_features().getLast());
    }

    @Test
    public void testRawUpdateCommand() {
        Map<String, Object> update_data = new HashMap<>() {{
            put("name", "Ünal");
            put("age", 23);
        }};
        MockCommandGenerator generator = new MockCommandGenerator();
        UpdateCommand update_command = (UpdateCommand) generator.update("students", update_data);

        assertEquals(CommandType.UPDATE, update_command.getCommandType());
        assertEquals("students", update_command.getTarget());
        assertEquals(2, update_command.getUpdateData().size());
        assertTrue(update_command.getUpdateData().containsKey("name"));
        assertTrue(update_command.getUpdateData().containsKey("age"));
        assertEquals("Ünal", update_command.getUpdateData().get("name"));
        assertEquals(23, update_command.getUpdateData().get("age"));
    }
}
