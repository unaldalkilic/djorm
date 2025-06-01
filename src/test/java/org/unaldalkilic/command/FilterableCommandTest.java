package org.unaldalkilic.command;

import org.junit.jupiter.api.Test;
import org.unaldalkilic.command.command_filter.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.unaldalkilic.command.MockCommandGenerator.equal;
import static org.unaldalkilic.command.MockCommandGenerator.and;
import static org.unaldalkilic.command.MockCommandGenerator.or;
import static org.unaldalkilic.command.MockCommandGenerator.lessThan;

public class FilterableCommandTest {
    @Test
    public void testSimpleFilterableCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        FilterableCommand delete_command = generator.delete("students")
                .where(equal("name", "Ünal"));

        assertEquals(CommandFilterLeafNode.class, delete_command.getRootFilterNode().getClass());
        assertEquals("name", ((CommandFilterLeafNode) delete_command.getRootFilterNode()).getField());
        assertEquals("Ünal", ((CommandFilterLeafNode) delete_command.getRootFilterNode()).getValue());
        assertEquals(CommandOperator.EQUAL, ((CommandFilterLeafNode) delete_command.getRootFilterNode()).getOperator());
    }

    @Test
    public void testSimpleFilterableCommandWithNot() {
        MockCommandGenerator generator = new MockCommandGenerator();
        FilterableCommand delete_command = generator.delete("students")
                .where(equal("name", "Ünal").not());

        assertTrue(delete_command.getRootFilterNode().isNegated());
    }

    @Test
    public void testMultipleFilterableCommand() {
        MockCommandGenerator generator = new MockCommandGenerator();
        FilterableCommand delete_command = generator.delete("students")
                .where(and(
                        equal("name", "Ünal"),
                        lessThan("age", 30),
                        equal("surname", "Dalkılıç")
                ));

        assertEquals(CommandLogicalType.AND, ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getType());
        assertEquals(3, ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().size());

        assertEquals("name", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getFirst()).getField());
        assertEquals("Ünal", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getFirst()).getValue());
        assertEquals(CommandOperator.EQUAL, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getFirst()).getOperator());

        assertEquals("age", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().get(1)).getField());
        assertEquals(30, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().get(1)).getValue());
        assertEquals(CommandOperator.LESS, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().get(1)).getOperator());

        assertEquals("surname", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getField());
        assertEquals("Dalkılıç", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getValue());
        assertEquals(CommandOperator.EQUAL, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getOperator());
    }

    @Test
    public void testComplexFilterableCommandSet() {
        MockCommandGenerator generator = new MockCommandGenerator();
        FilterableCommand delete_command = generator.delete("students")
                .where(or(
                        equal("name", "Ünal").not(),
                        and(
                                lessThan("age", 30),
                                equal("surname", "Dalkılıç")
                        ).not()
                ).not());

        assertEquals(CommandLogicalType.OR, ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getType());
        assertEquals(2, ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().size());
        assertTrue(((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).isNegated());

        assertEquals("name", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getFirst()).getField());
        assertEquals("Ünal", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getFirst()).getValue());
        assertEquals(CommandOperator.EQUAL, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getFirst()).getOperator());
        assertTrue(((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getFirst()).isNegated());

        assertEquals(CommandLogicalType.AND, ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getType());
        assertEquals(2, ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getChildren().size());
        assertTrue(((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).isNegated());

        assertEquals(CommandOperator.LESS, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getChildren().getFirst()).getOperator());
        assertEquals("age", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getChildren().getFirst()).getField());
        assertEquals(30, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getChildren().getFirst()).getValue());

        assertEquals(CommandOperator.EQUAL, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getChildren().getLast()).getOperator());
        assertEquals("surname", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getChildren().getLast()).getField());
        assertEquals("Dalkılıç", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) delete_command.getRootFilterNode()).getChildren().getLast()).getChildren().getLast()).getValue());
    }
}
