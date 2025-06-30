package org.unaldalkilic.query_generator;

import org.unaldalkilic.command.Command;
import org.unaldalkilic.command.command_filter.CommandFilterNode;
import org.unaldalkilic.command.command_sort.SortableCommand;

public interface QueryGenerator {
    public String auto(Command command);
    public String select(Command command);
    public String update(Command command);
    public String delete(Command command);
    public String insert(Command command);

    public String filter_extension(CommandFilterNode filter_root);
    public String sorting_extension(SortableCommand sortable_command);
}
