package org.unaldalkilic.command.command_filter;

import org.unaldalkilic.command.Command;

public abstract class FilterableCommand extends Command {
    private CommandFilterNode rootFilter;

    public FilterableCommand(String target) {
        super(target);
    }

    public CommandFilterNode getRootFilterNode() {
        return rootFilter;
    }
    public FilterableCommand where(CommandFilterNode filter_node) {rootFilter = filter_node; return this;}
}
