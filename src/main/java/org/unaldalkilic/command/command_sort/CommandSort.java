package org.unaldalkilic.command.command_sort;

import java.util.HashMap;
import java.util.Map;

public class CommandSort implements SortableBasis {
    private final Map<String, CommandSortDirection> sorts;

    public CommandSort() {
        sorts = new HashMap<>();
    }

    public Map<String, CommandSortDirection> getSorts() {
        return sorts;
    }

    @Override
    public SortableBasis sort(Object... sorts) {
        for (Object obj : sorts) {
            switch (obj) {
                case Map<?, ?> map -> {
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        if (entry.getKey() instanceof String key &&
                                entry.getValue() instanceof CommandSortDirection dir) {
                            this.sorts.put(key, dir);
                        } else {
                            throw new IllegalArgumentException("Map must be of type Map<String, CommandSortDirection>");
                        }
                    }
                }
                case String s -> this.sorts.put(s, CommandSortDirection.ASC);
                case null, default ->
                        throw new IllegalArgumentException("sort(...) only accepts String or Map<String, CommandSortDirection>");
            }
        }
        return this;
    }

}
