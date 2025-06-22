package org.unaldalkilic.test_utils;

import org.unaldalkilic.command.DeleteCommand;
import org.unaldalkilic.command.InsertCommand;
import org.unaldalkilic.command.SelectCommand;
import org.unaldalkilic.command.UpdateCommand;

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
