package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.FilterableCommand;
import org.unaldalkilic.command.command_sort.CommandSort;
import org.unaldalkilic.command.command_sort.SortableCommand;

public class SelectCommand extends FilterableCommand implements SortableCommand {
    private final CommandSort commandSort;

    public SelectCommand(String target) {
        super(target);
        commandSort = new CommandSort();
    }

    public CommandSort getCommandSort() { return commandSort; }

    @Override
    public CommandType getCommandType() {
        return CommandType.SELECT;
    }

    @Override
    public SortableCommand sort(Object... sorts) {
        commandSort.sort(sorts); // Change the inner state of the CommandSort object
        return this; // Return Command object; which means itself
    }
}
