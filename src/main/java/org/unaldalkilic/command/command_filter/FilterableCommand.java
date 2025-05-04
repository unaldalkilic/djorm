package org.unaldalkilic.command.command_filter;

public interface FilterableCommand {
    public CommandFilterNode getRootFilterNode();
    public void setRootFilterNode(CommandFilterNode rootFilterNode);
}
