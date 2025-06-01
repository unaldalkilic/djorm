package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.CommandFilter;
import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_filter.FilterableBasis;
import org.unaldalkilic.command.command_filter.FilterableCommand;

public class DeleteCommand extends Command implements FilterableCommand {
    private final CommandFilter commandFilter;

    public DeleteCommand(String target) {
        super(target);
        commandFilter = new CommandFilter();
    }

    @Override
    public CommandFilter getCommandFilter() { return commandFilter; }

    @Override
    public CommandType getCommandType() {
        return CommandType.DELETE;
    }

    @Override
    public FilterableBasis where(CommandFilterNode filter_node) {
        commandFilter.where(filter_node); // Change the inner state of the CommandFilter object
        return this; // Return Command object; which means itself
    }
}

