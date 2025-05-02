package org.unaldalkilic.util.execution_result;

import org.unaldalkilic.enums.DatabaseType;

import java.util.List;
import java.util.Map;

public class MongoExecutionResult extends ExecutionResult{
    public MongoExecutionResult(DatabaseType database_type, int affected_rows, List<Map<String, Object>> results, Exception exception) {
        super(database_type, affected_rows, results, exception);
    }

    // TODO later, first implement SQL
}
