package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.CommandFilter;
import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_filter.FilterableCommand;
import org.unaldalkilic.command.command_sort.CommandSort;
import org.unaldalkilic.command.command_sort.SortableCommand;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand extends Command implements FilterableCommand, SortableCommand {
    private final CommandFilter commandFilter;
    private final CommandSort commandSort;
    private final List<String> selected_features;

    public SelectCommand(String target) {
        this(target, new ArrayList<>());
    }

    public SelectCommand(String target, List<String> selected_features) {
        super(target);
        commandFilter = new CommandFilter();
        commandSort = new CommandSort();
        this.selected_features = selected_features;
    }

    public List<String> getSelected_features() { return selected_features; }
    public boolean is_all_selected() { return selected_features.isEmpty(); }

    @Override
    public CommandFilter getCommandFilter() { return commandFilter; }

    @Override
    public CommandSort getCommandSort() { return commandSort; }

    @Override
    public CommandType getCommandType() {
        return CommandType.SELECT;
    }

    @Override
    public SelectCommand where(CommandFilterNode filter_node) {
        commandFilter.where(filter_node); // Change the inner state of the CommandFilter object
        return this; // Return Command object; which means itself
    }

    @Override
    public SelectCommand sort(Object... sorts) {
        commandSort.sort(sorts); // Change the inner state of the CommandSort object
        return this; // Return Command object; which means itself
    }
}
