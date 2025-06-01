package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MockCommandGenerator {
    public DeleteCommand delete(String target) {
        return new DeleteCommand(target);
    }
    public InsertCommand insert(String target, Map<String, Object> insert_data) {
        return new InsertCommand(target, insert_data);
    }
    public SelectCommand select(String target) {
        return new SelectCommand(target);
    }
    public UpdateCommand update(String target, Map<String, Object> update_data) {
        return new UpdateCommand(target, update_data);
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
