package org.unaldalkilic.query_generator;

import org.unaldalkilic.command.*;
import org.unaldalkilic.command.command_filter.CommandFilterLeafNode;
import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_filter.CommandLogicalFilterGroupNode;
import org.unaldalkilic.command.command_filter.CommandOperator;
import org.unaldalkilic.command.command_sort.CommandSortDirection;
import org.unaldalkilic.command.command_sort.SortableCommand;

import java.util.Map;

public abstract class SQLQueryGenerator {
    public static String select(Command command) {
        if (command.getCommandType() != CommandType.SELECT)
            throw new IllegalArgumentException("Given command is not a SELECT command!");
        SelectCommand select_command = (SelectCommand) command;

        String result = "SELECT * FROM " + select_command.getTarget() + " "; // TODO assume * for now; then the select filters will be added.

        // Check for CommandFilter WHERE
        if (!select_command.getCommandFilter().isEmpty()) {
            result += "WHERE ";
            CommandFilterNode filter_root = select_command.getCommandFilter().getRootFilterNode();
            result += filter_extension(filter_root) + " ";
        }

        // Check for CommandSort ORDER BY
        if (!select_command.getCommandSort().isEmpty())
            result += "ORDER BY " + sorting_extension(select_command) + " ";

        result += ";";
        return result;
    }

    public static String delete(Command command) {
        if (command.getCommandType() != CommandType.DELETE)
            throw new IllegalArgumentException("Given command is not a DELETE command!");
        DeleteCommand delete_command = (DeleteCommand) command;

        String result = "DELETE FROM " + delete_command.getTarget() + " ";

        // Check for CommandFilter WHERE
        if (!delete_command.getCommandFilter().isEmpty()) {
            result += "WHERE ";
            CommandFilterNode filter_root = delete_command.getCommandFilter().getRootFilterNode();
            result += filter_extension(filter_root) + " ";
        }

        result += ";";
        return result;
    }

    public static String insert(Command command) {
        if (command.getCommandType() != CommandType.INSERT)
            throw new IllegalArgumentException("Given command is not a INSERT command!");
        InsertCommand insert_command = (InsertCommand) command;

        StringBuilder result = new StringBuilder();
        result.append("INSERT INTO ").append(insert_command.getTarget()).append(" ");

        result.append("(");
        for (String features: insert_command.getInsertData().keySet())
            result.append(features).append(",");
        result.deleteCharAt(-1);

        result.append(" ").append("VALUES").append(" ");

        result.append("(");
        for (Object values: insert_command.getInsertData().values())
            result.append(values.toString()).append(",");
        result.deleteCharAt(-1);

        result.append(";");
        return result.toString();
    }

    public static String update(Command command) {
        if (command.getCommandType() != CommandType.UPDATE)
            throw new IllegalArgumentException("Given command is not a UPDATE command!");
        UpdateCommand update_command = (UpdateCommand) command;

        StringBuilder result = new StringBuilder();
        result.append("UPDATE ").append(update_command.getTarget()).append(" ").append("SET").append(" ");

        for (String feature: update_command.getUpdateData().keySet())
            result.append(feature).append("=").append(update_command.getUpdateData().get(feature).toString()).append(",");
        result.deleteCharAt(-1);

        result.append(" ");

        // Check for CommandFilter WHERE
        if (!update_command.getCommandFilter().isEmpty()) {
            result.append("WHERE ");
            CommandFilterNode filter_root = update_command.getCommandFilter().getRootFilterNode();
            result.append(filter_extension(filter_root)).append(" ");
        }

        result.append(";");
        return result.toString();
    }

    private static String filter_extension(CommandFilterNode filter_root) {
        StringBuilder result = new StringBuilder();
        if (filter_root instanceof CommandFilterLeafNode temp) {
            result.append(temp.getField()).append(" ");
            switch (temp.getOperator()) {
                case CommandOperator.EQUAL -> result.append("= ");
                case CommandOperator.GREATER -> result.append("> ");
                // TODO case CommandOperator.IN -> result += "";
                case CommandOperator.GREATER_EQUAL -> result.append(">= ");
                case CommandOperator.LESS -> result.append("< ");
                case CommandOperator.LESS_EQUAL -> result.append("<= ");
                case CommandOperator.LIKE -> result.append("LIKE ");
                default -> throw new IllegalStateException("Undefined operator type for filter node");
            }
            result.append(temp.getValue().toString());
        }
        else if (filter_root instanceof CommandLogicalFilterGroupNode temp) {
            result.append("(");
            for (int i = 0; i < temp.getChildren().size(); i++) {
                CommandFilterNode filter_node = temp.getChildren().get(i);
                result.append(filter_extension(filter_node));
                if (! (i >= temp.getChildren().size() - 1))
                    result.append(" ").append(temp.getType().toString()).append(" ");
            }
            result.append(")");
        }
        else
            throw new IllegalStateException("CommandFilterNode argument must be either a CommandFilterLeafNode or CommandLogicalFilterGroupNode");

        return result.toString();
    }

    private static String sorting_extension(SortableCommand sortable_command) {
        StringBuilder result = new StringBuilder();
        Map<String, CommandSortDirection> sorts = sortable_command.getCommandSort().getSorts();

        for (String feature: sorts.keySet())
            result.append(feature).append(sorts.get(feature).toString()).append(",");
        result.deleteCharAt(-1);

        return result.toString();
    }
}
