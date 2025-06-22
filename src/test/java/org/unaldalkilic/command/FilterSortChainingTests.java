package org.unaldalkilic.command;

import org.junit.jupiter.api.Test;
import org.unaldalkilic.command.command_filter.CommandFilterLeafNode;
import org.unaldalkilic.command.command_filter.CommandLogicalFilterGroupNode;
import org.unaldalkilic.command.command_filter.CommandLogicalType;
import org.unaldalkilic.command.command_filter.CommandOperator;
import org.unaldalkilic.command.command_sort.CommandSortDirection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.unaldalkilic.command.command_filter.FilterableBasis.*;
import static org.unaldalkilic.command.command_sort.SortableBasis.asc;
import static org.unaldalkilic.command.command_sort.SortableBasis.desc;

public class FilterSortChainingTests {
    @Test
    public void SimpleFilterSortChainingTest() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SelectCommand selectCommand = generator.select("students");

        selectCommand.where(equal("name", "Ünal")).sort(desc("age"));

        assertEquals("name", ((CommandFilterLeafNode) selectCommand.getCommandFilter().getRootFilterNode()).getField());
        assertEquals("Ünal", ((CommandFilterLeafNode) selectCommand.getCommandFilter().getRootFilterNode()).getValue());

        assertTrue(selectCommand.getCommandSort().getSorts().containsKey("age"));
        assertEquals(CommandSortDirection.DESC, selectCommand.getCommandSort().getSorts().get("age"));
    }

    @Test
    public void ComplexFilterSortChainingTest() {
        MockCommandGenerator generator = new MockCommandGenerator();
        SelectCommand selectCommand = generator.select("students");

        selectCommand.where(
                and(
                        or(
                            equal("name", "Ünal"), lessThan("age", 23)
                        ),
                        equal("surname", "Dalkılıç").not(),
                        lessThan("salary", 60000)
                ).not()
        ).sort(
                asc("name"),
                "surname",
                desc("age", "salary", "credit")
        );

        assertTrue(selectCommand.getCommandFilter().getRootFilterNode().isNegated());
        assertTrue(((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().get(1).isNegated());

        assertEquals(CommandLogicalType.AND, ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getType());
        assertEquals(CommandLogicalType.OR, ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getFirst()).getType());

        assertEquals(CommandOperator.EQUAL, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getFirst()).getChildren().getFirst()).getOperator());
        assertEquals("name", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getFirst()).getChildren().getFirst()).getField());
        assertEquals("Ünal", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getFirst()).getChildren().getFirst()).getValue());
        assertEquals(CommandOperator.LESS, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getFirst()).getChildren().getLast()).getOperator());
        assertEquals("age", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getFirst()).getChildren().getLast()).getField());
        assertEquals(23, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getFirst()).getChildren().getLast()).getValue());

        assertEquals(CommandOperator.EQUAL, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().get(1)).getOperator());
        assertEquals("surname", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().get(1)).getField());
        assertEquals("Dalkılıç", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().get(1)).getValue());

        assertEquals(CommandOperator.LESS, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getLast()).getOperator());
        assertEquals("salary", ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getLast()).getField());
        assertEquals(60000, ((CommandFilterLeafNode) ((CommandLogicalFilterGroupNode) selectCommand.getCommandFilter().getRootFilterNode()).getChildren().getLast()).getValue());

        assertEquals(5, selectCommand.getCommandSort().getSorts().size());

        assertTrue(selectCommand.getCommandSort().getSorts().containsKey("name"));
        assertTrue(selectCommand.getCommandSort().getSorts().containsKey("surname"));
        assertTrue(selectCommand.getCommandSort().getSorts().containsKey("age"));
        assertTrue(selectCommand.getCommandSort().getSorts().containsKey("salary"));
        assertTrue(selectCommand.getCommandSort().getSorts().containsKey("credit"));

        assertEquals(CommandSortDirection.ASC, selectCommand.getCommandSort().getSorts().get("name"));
        assertEquals(CommandSortDirection.ASC, selectCommand.getCommandSort().getSorts().get("surname"));
        assertEquals(CommandSortDirection.DESC, selectCommand.getCommandSort().getSorts().get("age"));
        assertEquals(CommandSortDirection.DESC, selectCommand.getCommandSort().getSorts().get("salary"));
        assertEquals(CommandSortDirection.DESC, selectCommand.getCommandSort().getSorts().get("credit"));
    }
}
