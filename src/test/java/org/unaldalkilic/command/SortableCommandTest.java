package org.unaldalkilic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.unaldalkilic.command.command_sort.CommandSortDirection;
import org.unaldalkilic.command.command_sort.SortableBasis;
import org.unaldalkilic.command.command_sort.SortableCommand;

import static org.unaldalkilic.command.command_sort.SortableBasis.asc;
import static org.unaldalkilic.command.command_sort.SortableBasis.desc;

public class SortableCommandTest {
    @Test
    public void testSimpleSortableCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SortableCommand select_command = (SortableCommand) generator.select("students");
        select_command.sort("name", "age");

        assertEquals(2, select_command.getCommandSort().getSorts().size());
        assertTrue(select_command.getCommandSort().getSorts().containsKey("name"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("age"));
        assertEquals(CommandSortDirection.ASC, select_command.getCommandSort().getSorts().get("name"));
        assertEquals(CommandSortDirection.ASC, select_command.getCommandSort().getSorts().get("age"));
    }

    @Test
    public void testSimpleSortableCommandWithASC() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SortableCommand select_command = (SortableCommand) generator.select("students");
        select_command.sort(asc("name", "age"));

        assertEquals(2, select_command.getCommandSort().getSorts().size());
        assertTrue(select_command.getCommandSort().getSorts().containsKey("name"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("age"));
        assertEquals(CommandSortDirection.ASC, select_command.getCommandSort().getSorts().get("name"));
        assertEquals(CommandSortDirection.ASC, select_command.getCommandSort().getSorts().get("age"));
    }

    @Test
    public void testSimpleSortableCommandWithDESC() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SortableCommand select_command = (SortableCommand) generator.select("students");
        select_command.sort(desc("name", "age"));

        assertEquals(2, select_command.getCommandSort().getSorts().size());
        assertTrue(select_command.getCommandSort().getSorts().containsKey("name"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("age"));
        assertEquals(CommandSortDirection.DESC, select_command.getCommandSort().getSorts().get("name"));
        assertEquals(CommandSortDirection.DESC, select_command.getCommandSort().getSorts().get("age"));
    }

    @Test
    public void testMixedASCDESCSortableCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SortableCommand select_command = (SortableCommand) generator.select("students");
        select_command.sort(desc("name", "age"), asc("surname"));

        assertEquals(3, select_command.getCommandSort().getSorts().size());
        assertTrue(select_command.getCommandSort().getSorts().containsKey("name"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("age"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("surname"));
        assertEquals(CommandSortDirection.DESC, select_command.getCommandSort().getSorts().get("name"));
        assertEquals(CommandSortDirection.DESC, select_command.getCommandSort().getSorts().get("age"));
        assertEquals(CommandSortDirection.ASC, select_command.getCommandSort().getSorts().get("surname"));
    }

    @Test
    public void testComplexSortableCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SortableCommand select_command = (SortableCommand) generator.select("students");
        select_command.sort("name", desc("surname"), "age", asc("something"), desc("blabla"));

        assertEquals(5, select_command.getCommandSort().getSorts().size());
        assertTrue(select_command.getCommandSort().getSorts().containsKey("name"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("age"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("surname"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("something"));
        assertTrue(select_command.getCommandSort().getSorts().containsKey("blabla"));
        assertEquals(CommandSortDirection.ASC, select_command.getCommandSort().getSorts().get("name"));
        assertEquals(CommandSortDirection.ASC, select_command.getCommandSort().getSorts().get("age"));
        assertEquals(CommandSortDirection.DESC, select_command.getCommandSort().getSorts().get("surname"));
        assertEquals(CommandSortDirection.ASC, select_command.getCommandSort().getSorts().get("something"));
        assertEquals(CommandSortDirection.DESC, select_command.getCommandSort().getSorts().get("blabla"));
    }
}
