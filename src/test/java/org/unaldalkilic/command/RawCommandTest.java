package org.unaldalkilic.command;

import org.junit.jupiter.api.Test;
import org.unaldalkilic.command.command_filter.FilterableCommand;

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
        Command insert_command = generator.insert("students", insert_data);

        assertEquals(CommandType.INSERT, insert_command.getCommandType());
        assertEquals("students", insert_command.getTarget());
        assertEquals(2, ((InsertCommand) insert_command).getInsertData().size());
        assertTrue(((InsertCommand) insert_command).getInsertData().containsKey("name"));
        assertTrue(((InsertCommand) insert_command).getInsertData().containsKey("age"));
        assertEquals("Ünal", ((InsertCommand) insert_command).getInsertData().get("name"));
        assertEquals(23, ((InsertCommand) insert_command).getInsertData().get("age"));
    }

    @Test
    public void testRawSelectCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command select_command = generator.select("students");

        assertEquals(CommandType.SELECT, select_command.getCommandType());
        assertEquals("students", select_command.getTarget());
    }

    @Test
    public void testRawUpdateCommand() {
        Map<String, Object> update_data = new HashMap<>() {{
            put("name", "Ünal");
            put("age", 23);
        }};
        MockCommandGenerator generator = new MockCommandGenerator();
        FilterableCommand update_command = generator.update("students", update_data);

        assertEquals(CommandType.UPDATE, update_command.getCommandType());
        assertEquals("students", update_command.getTarget());
        assertEquals(2, ((UpdateCommand) update_command).getUpdateData().size());
        assertTrue(((UpdateCommand) update_command).getUpdateData().containsKey("name"));
        assertTrue(((UpdateCommand) update_command).getUpdateData().containsKey("age"));
        assertEquals("Ünal", ((UpdateCommand) update_command).getUpdateData().get("name"));
        assertEquals(23, ((UpdateCommand) update_command).getUpdateData().get("age"));
    }
}
