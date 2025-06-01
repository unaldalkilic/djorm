package org.unaldalkilic.command.command_sort;

import java.util.HashMap;
import java.util.Map;

public interface SortableBasis {
    public SortableBasis sort(Object... sorts);

    public static Map<String, CommandSortDirection> asc(String... fields) {
        Map<String, CommandSortDirection> result = new HashMap<>();
        for (String field: fields)
            result.put(field, CommandSortDirection.ASC);
        return result;
    }

    public static Map<String, CommandSortDirection> desc(String... fields) {
        Map<String, CommandSortDirection> result = new HashMap<>();
        for (String field: fields)
            result.put(field, CommandSortDirection.DESC);
        return result;
    }
}
