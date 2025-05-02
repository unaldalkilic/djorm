package org.unaldalkilic.util.execution_result;

import org.junit.jupiter.api.Test;
import org.unaldalkilic.enums.DatabaseType;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SQLExecutionResultTest {

    @Test
    public void testFromResultSet_success() throws Exception {
        ResultSet mockResultSet = simleMockResultSet();

        // Test edilen metot
        SQLExecutionResult result = SQLExecutionResult.from_result_set(mockResultSet, DatabaseType.POSTGRESQL);

        assertTrue(result.is_success());
        assertTrue(result.has_results());

        List<Map<String, Object>> rows = result.getResults();
        assertEquals(1, rows.size());

        Map<String, Object> row = rows.getFirst();
        assertEquals(1, row.get("id"));
        assertEquals("Alice", row.get("name"));
    }

    @Test
    public void testFromResultSet_nullResultSet() {
        SQLExecutionResult result = SQLExecutionResult.from_result_set(null, DatabaseType.MYSQL);

        assertTrue(result.is_success());
        assertFalse(result.has_results());
        assertNotNull(result.getResults());
        assertEquals(0, result.getResults().size());
    }

    @Test
    public void testFromResultSet_exception() throws Exception {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getMetaData()).thenThrow(new RuntimeException("Simulated DB error"));

        SQLExecutionResult result = SQLExecutionResult.from_result_set(mockResultSet, DatabaseType.MYSQL);

        assertFalse(result.is_success());
        assertNotNull(result.getException());
        assertEquals("Simulated DB error", result.getException().getMessage());
    }

    @Test
    public void testFromResultSet_nullDatabaseType() throws Exception{
        assertThrows(IllegalArgumentException.class, () -> SQLExecutionResult.from_result_set(simleMockResultSet(), null));
    }

    @Test
    public void testFromResultSet_incompatibleDatabaseType() throws Exception{
        assertThrows(IllegalArgumentException.class, () -> SQLExecutionResult.from_result_set(simleMockResultSet(), DatabaseType.MONGO));
    }

    @Test
    public void testFromAffectedRows() {
        SQLExecutionResult result = SQLExecutionResult.from_affected_rows(2, DatabaseType.MSQL);

        assertTrue(result.is_success());
        assertEquals(2, result.getAffected_rows());
        assertNull(result.getResults());
        assertNull(result.getException());
    }

    @Test
    public void testFromAffectedRows_lessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> SQLExecutionResult.from_affected_rows(-1, DatabaseType.MYSQL));
    }

    @Test
    public void testFromAffectedRows_nullDatabaseType() {
        assertThrows(IllegalArgumentException.class, () -> SQLExecutionResult.from_affected_rows(1, null));
    }

    @Test
    public void testFromAffectedRows_incompatibleDatabaseType() {
        assertThrows(IllegalArgumentException.class, () -> SQLExecutionResult.from_affected_rows(1, DatabaseType.MONGO));
    }

    @Test
    public void testFromException() {
        SQLException exception = new SQLException("dummy sql error");
        SQLExecutionResult executionResult = SQLExecutionResult.from_exception(exception, DatabaseType.MSQL);

        assertFalse(executionResult.is_success());
        assertEquals(0, executionResult.getAffected_rows());
        assertNull(executionResult.getResults());
        assertFalse(executionResult.has_results());
        assertEquals(exception.getMessage(), executionResult.getException().getMessage());
    }

    @Test
    public void testFromException_nullDatabaseType() {
        assertThrows(IllegalArgumentException.class, () -> SQLExecutionResult.from_exception(new SQLException(), null));
    }

    @Test
    public void testFromException_incompatibleDatabaseType() {
        assertThrows(IllegalArgumentException.class, () -> SQLExecutionResult.from_exception(new SQLException(), DatabaseType.MONGO));
    }

    private static ResultSet simleMockResultSet() throws Exception{
        // Mock ResultSet ve MetaData
        ResultSet mockResultSet = mock(ResultSet.class);
        ResultSetMetaData mockMeta = mock(ResultSetMetaData.class);

        // Metadata davranışı: 2 sütun (id, name)
        when(mockResultSet.getMetaData()).thenReturn(mockMeta);
        when(mockMeta.getColumnCount()).thenReturn(2);
        when(mockMeta.getColumnLabel(1)).thenReturn("id");
        when(mockMeta.getColumnLabel(2)).thenReturn("name");

        // ResultSet davranışı: 1 satır, id=1, name="Alice"
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getObject(1)).thenReturn(1);
        when(mockResultSet.getObject(2)).thenReturn("Alice");

        return mockResultSet;
    }
}
