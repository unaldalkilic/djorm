package org.unaldalkilic.command.command_filter;

public abstract class CommandFilterNode {
    protected boolean negated = false;

    public boolean isNegated() {
        return negated;
    }
    public CommandFilterNode not() {negated = !negated; return this;}
}
