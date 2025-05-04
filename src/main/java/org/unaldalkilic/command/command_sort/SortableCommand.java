package org.unaldalkilic.command.command_sort;

import java.util.List;

public interface SortableCommand {
    public List<CommandSort> getCommandSortList();
    public void addToCommandSortList(CommandSort commandSort);
}
