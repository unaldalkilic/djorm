package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_filter.FilterableCommand;
import org.unaldalkilic.command.command_sort.CommandSort;
import org.unaldalkilic.command.command_sort.SortableCommand;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand extends FilterableCommand implements SortableCommand {
    private final List<CommandSort> commandSortList;

    public SelectCommand(String target) {
        super(target);
        commandSortList = new ArrayList<>();
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.SELECT;
    }

    @Override
    public List<CommandSort> getCommandSortList() {
        return commandSortList;
    }

    @Override
    public void addToCommandSortList(CommandSort commandSort) {
        commandSortList.add(commandSort);
    }
}
