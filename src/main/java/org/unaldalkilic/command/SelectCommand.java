package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.CommandFilter;
import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_filter.FilterableBasis;
import org.unaldalkilic.command.command_filter.FilterableCommand;
import org.unaldalkilic.command.command_sort.CommandSort;
import org.unaldalkilic.command.command_sort.SortableBasis;
import org.unaldalkilic.command.command_sort.SortableCommand;

public class SelectCommand extends Command implements FilterableCommand, SortableCommand {
    private final CommandFilter commandFilter;
    private final CommandSort commandSort;

    public SelectCommand(String target) {
        super(target);
        commandFilter = new CommandFilter();
        commandSort = new CommandSort();
    }

    @Override
    public CommandFilter getCommandFilter() { return commandFilter; }

    @Override
    public CommandSort getCommandSort() { return commandSort; }

    @Override
    public CommandType getCommandType() {
        return CommandType.SELECT;
    }

    @Override
    public FilterableBasis where(CommandFilterNode filter_node) {
        commandFilter.where(filter_node); // Change the inner state of the CommandFilter object
        return this; // Return Command object; which means itself
    }

    @Override
    public SortableBasis sort(Object... sorts) {
        commandSort.sort(sorts); // Change the inner state of the CommandSort object
        return this; // Return Command object; which means itself
    }
}
