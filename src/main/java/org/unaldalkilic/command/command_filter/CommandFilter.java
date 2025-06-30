package org.unaldalkilic.command.command_filter;

public class CommandFilter implements FilterableBasis {
    private CommandFilterNode rootFilter;

    public CommandFilterNode getRootFilterNode() {
        return rootFilter;
    }
    public boolean isEmpty() { return rootFilter == null; }

    @Override
    public FilterableBasis where(CommandFilterNode filter_node) {rootFilter = filter_node; return this;}
}
