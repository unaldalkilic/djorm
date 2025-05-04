package org.unaldalkilic.command.command_filter;

public abstract class CommandFilterNode {
    protected boolean negated = false;

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }
}
