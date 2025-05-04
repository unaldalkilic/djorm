package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_filter.FilterableCommand;

import java.util.Map;

public class UpdateCommand extends Command implements FilterableCommand {
    private final Map<String, Object> updateData;
    private CommandFilterNode rootFilter;

    public UpdateCommand(String target, Map<String, Object> updateData) {
        super(target);
        this.updateData = updateData;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.UPDATE;
    }

    public Map<String, Object> getUpdateData() {
        return updateData;
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
