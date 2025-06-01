package org.unaldalkilic.command;

public abstract class Command {
    private final String target;

    public Command(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public abstract CommandType getCommandType();
}
