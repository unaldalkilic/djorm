package org.unaldalkilic.command.command_sort;

public record CommandSort(String field, CommandSortDirection direction) {

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CommandSort temp = (CommandSort) obj;
        return field.equals(temp.field()) && (direction == temp.direction());
    }
}
