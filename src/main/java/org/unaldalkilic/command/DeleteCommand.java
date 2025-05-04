package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_filter.FilterableCommand;

public class DeleteCommand extends Command implements FilterableCommand {
    private CommandFilterNode rootFilter;

    public DeleteCommand(String target) {
        super(target);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.DELETE;
    }

    @Override
    public CommandFilterNode getRootFilterNode() {
        return rootFilter;
    }

    @Override
    public void setRootFilterNode(CommandFilterNode rootFilterNode) {
        rootFilter = rootFilterNode;
    }
}

