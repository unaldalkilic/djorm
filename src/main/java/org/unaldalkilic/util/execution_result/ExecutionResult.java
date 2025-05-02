package org.unaldalkilic.util.execution_result;

import org.unaldalkilic.enums.DatabaseCategory;
import org.unaldalkilic.enums.DatabaseType;

import java.util.*;

public abstract class ExecutionResult {
    private final DatabaseType database_type;
    private final int affected_rows;
    private final List<Map<String, Object>> results;
    private final Exception exception;


    public ExecutionResult(DatabaseType database_type, int affected_rows, List<Map<String, Object>> results, Exception exception) {
        if (database_type == null || affected_rows < 0)
            throw new IllegalArgumentException("database_type cannot be null and affected_rows must be a non-negative integer.");

        this.database_type = database_type;
        this.affected_rows = affected_rows;
        this.results = clone_results(results); // prevent privacy leak
        this.exception = exception;
    }

    public ExecutionResult(ExecutionResult execution_result) {
        this(execution_result.getDatabase_type(), execution_result.getAffected_rows(), execution_result.getResults(), execution_result.getException());
    }

    public DatabaseType getDatabase_type() {
        return database_type;
    }

    public int getAffected_rows() {
        return affected_rows;
    }

    public List<Map<String, Object>> getResults() {
        // To prevent privacy leak, copy every content of the results
        return clone_results(results);
    }

    public Exception getException() {
        return exception;
    }

    private static List<Map<String, Object>> clone_results(List<Map<String, Object>> target) {
        if (target == null)
            return null;

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> item: target) {
            Map<String, Object> map_item = new HashMap<>(item);
            result.add(map_item);
        }
        return result;
    }

    private static boolean equals_results(List<Map<String, Object>> results_1, List<Map<String, Object>> results_2) {
        if (results_2 == null) return results_1 == null;
        if (results_2.size() != results_1.size()) return false;

        for (int i = 0; i < results_1.size(); i++)
            if (! results_1.get(i).equals(results_2.get(i))) return false;

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        ExecutionResult temp = (ExecutionResult) obj;
        return (database_type == temp.getDatabase_type()) && (affected_rows == temp.getAffected_rows()) && (equals_results(results, temp.getResults())) && Objects.equals(exception, temp.getException());
    }

    public DatabaseCategory get_database_category() {
        return database_type.get_category();
    }

    public boolean is_success() {
        return (exception == null) && ((affected_rows > 0) || (results != null));
    }

    public boolean has_results() {
        return (results != null) && (! results.isEmpty());
    }
}
