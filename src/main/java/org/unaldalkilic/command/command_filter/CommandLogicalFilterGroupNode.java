package org.unaldalkilic.command.command_filter;

import java.util.List;

public class CommandLogicalFilterGroupNode extends CommandFilterNode {
    private final CommandLogicalType type;
    private final List<CommandFilterNode> children;

    public CommandLogicalFilterGroupNode(CommandLogicalType type, List<CommandFilterNode> children) {
        this.type = type;
        this.children = children;
    }

    public CommandLogicalType getType() {
        return type;
    }

    public List<CommandFilterNode> getChildren() {
        return children;
    }
}