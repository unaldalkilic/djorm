package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockCommandGenerator {
    public FilterableCommand delete(String target) {
        return new DeleteCommand(target);
    }


    public static CommandFilterLeafNode equal(String field, Object value) { return new CommandFilterLeafNode(field, CommandOperator.EQUAL, value); }
    public static CommandFilterLeafNode lessThan(String field, Object value) { return new CommandFilterLeafNode(field, CommandOperator.LESS, value); }

    public static CommandLogicalFilterGroupNode and(CommandFilterNode... leaf_nodes) {
        List<CommandFilterNode> leaf_node_list = new ArrayList<>();
        Collections.addAll(leaf_node_list, leaf_nodes);
        return new CommandLogicalFilterGroupNode(CommandLogicalType.AND, leaf_node_list);
    }
    public static CommandLogicalFilterGroupNode or(CommandFilterNode... leaf_nodes) {
        List<CommandFilterNode> leaf_node_list = new ArrayList<>();
        Collections.addAll(leaf_node_list, leaf_nodes);
        return new CommandLogicalFilterGroupNode(CommandLogicalType.OR, leaf_node_list);
    }
}
