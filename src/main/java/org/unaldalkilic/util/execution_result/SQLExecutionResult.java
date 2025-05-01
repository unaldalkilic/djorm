package org.unaldalkilic.util.execution_result;

import org.unaldalkilic.enums.DatabaseType;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLExecutionResult extends ExecutionResult{
    public SQLExecutionResult(DatabaseType database_type, int affected_rows, List<Map<String, Object>> results, Exception exception) {
        super(database_type, affected_rows, results, exception);
    }

    // Static factory method for result set
    public static SQLExecutionResult from_result_set(ResultSet rs, DatabaseType database_type) {
        if (database_type == null)
            throw new IllegalArgumentException("DatabaseType argument cannot be null");

        try {
            List<Map<String, Object>> parsed_result_set = parseResultSet(rs);
            return new SQLExecutionResult(database_type, 0, parsed_result_set, null);
        } catch(Exception e) {
            return new SQLExecutionResult(database_type, 0, null, e);
        }
    }

    // Static factory method for affected_rows
    public static SQLExecutionResult from_affected_rows(int affected_rows, DatabaseType database_type) {
        if (affected_rows < 0 || database_type == null)
            throw new IllegalArgumentException("DatabaseType cannot be null and affected_rows must be a non-negative integer.");

        return new SQLExecutionResult(database_type, affected_rows, null, null);
    }

    private static List<Map<String, Object>> parseResultSet(ResultSet rs) throws SQLException {
        if (rs == null) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(meta.getColumnLabel(i), rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }
}
