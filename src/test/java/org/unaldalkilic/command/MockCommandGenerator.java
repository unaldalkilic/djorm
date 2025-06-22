package org.unaldalkilic.command;

import org.unaldalkilic.command.command_filter.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MockCommandGenerator {
    public DeleteCommand delete(String target) {
        return new DeleteCommand(target);
    }
    public InsertCommand insert(String target, Map<String, Object> insert_data) {
        return new InsertCommand(target, insert_data);
    }
    public SelectCommand select(String target) {
        return new SelectCommand(target);
    }
    public SelectCommand select(String target, String... selected_features) { return new SelectCommand(target, List.of(selected_features)); }
    public UpdateCommand update(String target, Map<String, Object> update_data) {
        return new UpdateCommand(target, update_data);
    }
}
