package org.unaldalkilic.command.command_filter;

public enum CommandLogicalType {
    AND, OR;

    @Override
    public String toString() {
        return switch (this) {
            case AND -> "AND";
            case OR -> "OR";
            default -> throw new IllegalStateException("CommandLogicalType can only be AND or OR");
        };
    }
}
