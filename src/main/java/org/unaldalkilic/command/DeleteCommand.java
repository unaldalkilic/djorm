package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.FilterableCommand;

public class DeleteCommand extends FilterableCommand {
    public DeleteCommand(String target) {
        super(target);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.DELETE;
    }
}

