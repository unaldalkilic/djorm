package org.unaldalkilic.command.command_filter;

public class CommandFilterLeafNode extends CommandFilterNode {
    private final CommandFilter filter;

    public CommandFilterLeafNode(CommandFilter filter) {
        this.filter = filter;
    }

    public CommandFilter getFilter() {
        return filter;
    }
}
