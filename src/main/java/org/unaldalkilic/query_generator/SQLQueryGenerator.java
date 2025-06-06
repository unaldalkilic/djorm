package org.unaldalkilic.query_generator;

import org.unaldalkilic.command.Command;
import org.unaldalkilic.command.CommandType;
import org.unaldalkilic.command.SelectCommand;
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

        String result = "SELECT * FROM "; // TODO assume * for now; then the select filters will be added.
        result = select_command.getTarget() + " ";

        // Check for CommandFilter WHERE
        if (select_command.getCommandFilter() != null) {
            result += "WHERE ";
            CommandFilterNode filter_root = select_command.getCommandFilter().getRootFilterNode();
            result += filter_extension(filter_root) + " ";
        }

        // Check for CommandSort ORDER BY
        if (select_command.getCommandSort() != null)
            result += "ORDER BY " + sorting_extension(select_command) + " ";

        result += ";";
        return result;
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
