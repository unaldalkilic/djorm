package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.CommandFilter;
import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_filter.FilterableBasis;
import org.unaldalkilic.command.command_filter.FilterableCommand;

import java.util.Map;

public class UpdateCommand extends Command implements FilterableCommand {
    private final Map<String, Object> updateData;
    private final CommandFilter commandFilter;

    public UpdateCommand(String target, Map<String, Object> updateData) {
        super(target);
        this.updateData = updateData;
        this.commandFilter = new CommandFilter();
    }

    @Override
    public CommandFilter getCommandFilter() { return commandFilter; }

    @Override
    public CommandType getCommandType() {
        return CommandType.UPDATE;
    }

    public Map<String, Object> getUpdateData() {
        return updateData;
    }

    @Override
    public FilterableBasis where(CommandFilterNode filter_node) {
        commandFilter.where(filter_node); // Change the inner state of the CommandFilter object
        return this; // Return Command object; which means itself
    }
}
