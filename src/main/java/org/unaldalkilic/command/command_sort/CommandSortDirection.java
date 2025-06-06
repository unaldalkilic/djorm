package org.unaldalkilic.command.command_sort;

public enum CommandSortDirection {
    ASC, DESC;

    @Override
    public String toString() {
        return switch (this) {
            case ASC -> "ASC";
            case DESC -> "DESC";
            default -> throw new IllegalStateException("CommandSortDirection must be either ASC or DESC");
        };
    }
}
