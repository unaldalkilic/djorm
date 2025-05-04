package org.unaldalkilic.command;

import java.util.Map;

public class InsertCommand extends Command {
    private final Map<String, Object> insertData;

    public InsertCommand(String target, Map<String, Object> insertData) {
        super(target);
        this.insertData = insertData;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.INSERT;
    }

    public Map<String, Object> getInsertData() {
        return insertData;
    }
}

