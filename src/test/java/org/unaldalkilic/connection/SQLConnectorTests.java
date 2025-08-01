package org.unaldalkilic.connection;

import org.junit.jupiter.api.*;
import org.unaldalkilic.command.Command;
import org.unaldalkilic.enums.DatabaseCategory;
import org.unaldalkilic.test_utils.MockCommandGenerator;
import org.unaldalkilic.util.execution_result.ExecutionResult;

import java.sql.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.unaldalkilic.command.command_filter.FilterableBasis.and;
import static org.unaldalkilic.command.command_filter.FilterableBasis.equal;

public class SQLConnectorTests extends ConnectionTestUtil{
    @BeforeEach
    public void resetDatabase() throws Exception {
        for (ConnectionTestCredential credential : credentials) {
            if (credential.type().get_category() != DatabaseCategory.SQL) continue;

            String url = "jdbc:"+credential.type()+"://" + credential.host() + ":" + credential.port() + "/" + credential.database_name();

            try (Connection connection = DriverManager.getConnection(url, credential.user(), credential.password())) {
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("DELETE FROM students");
                }

                try (PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO students (name, surname, age, gpa) VALUES (?, ?, ?, ?)")) {
                    pstmt.setString(1, "Ünal");
                    pstmt.setString(2, "Dalkılıç");
                    pstmt.setInt(3, 23);
                    pstmt.setDouble(4, 3.34);
                    pstmt.executeUpdate();
                }
            }
        }
    }

    @Test
    @Timeout(10000)
    @Tag("integration")
    public void testSimpleSQLConnector() throws SQLException {
        for (ConnectionTestCredential credential: credentials) {
            if (credential.type().get_category() != DatabaseCategory.SQL) continue;

            SQLConnector connector = SQLConnector.createSQLConnector(credential.host(), credential.port(), credential.user(), credential.password(), credential.database_name(), credential.type());
            assertFalse(connector.is_connected());
            connector.connect();
            assertTrue(connector.is_connected());
            assertNotNull(connector.getConnection());
            connector.disconnect();
            assertFalse(connector.is_connected());
        }
    }

    @Test
    @Timeout(10000)
    @Tag("integration")
    public void testSimpleSQLConnectorWithInsert() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command insert_command = generator.insert("students", new HashMap<>() {
            {
                put("name", "Burcu");
                put("surname", "Dalkılıç");
                put("age", 24);
                put("gpa", 3.5);
            }
        });

        for (ConnectionTestCredential credential : credentials) {
            if (credential.type().get_category() != DatabaseCategory.SQL) continue;

            SQLConnector connector = SQLConnector.createSQLConnector(credential.host(), credential.port(), credential.user(), credential.password(), credential.database_name(), credential.type());
            assertFalse(connector.is_connected());
            ExecutionResult executionResult = connector.execute(insert_command);
            assertTrue(connector.is_connected());
            assertEquals( 1, executionResult.getAffected_rows());
            connector.disconnect();
            assertFalse(connector.is_connected());
        }
    }

    @Test
    @Timeout(10000)
    @Tag("integration")
    public void testSimpleSQLConnectorWithSelectQuery() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command select_command = generator.select("students");

        for (ConnectionTestCredential credential : credentials) {
            if (credential.type().get_category() != DatabaseCategory.SQL) continue;

            SQLConnector connector = SQLConnector.createSQLConnector(credential.host(), credential.port(), credential.user(), credential.password(), credential.database_name(), credential.type());
            assertFalse(connector.is_connected());
            ExecutionResult result = connector.execute(select_command);
            assertTrue(connector.is_connected());

            assertEquals(1, result.getResults().size());
            assertEquals("Ünal", result.getResults().getFirst().get("name"));
            assertEquals("Dalkılıç", result.getResults().getFirst().get("surname"));
            assertEquals(23, result.getResults().getFirst().get("age"));
            assertEquals(3.34f, result.getResults().getFirst().get("gpa"));

            connector.disconnect();
            assertFalse(connector.is_connected());
        }
    }

    @Test
    @Timeout(10000)
    @Tag("integration")
    public void testSimpleSQLConnectorWithDelete() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command delete_command = generator.delete("students").where(
                and(
                        equal("name", "Ünal"),
                        equal("surname", "Dalkılıç")
                )
        );

        for (ConnectionTestCredential credential : credentials) {
            if (credential.type().get_category() != DatabaseCategory.SQL) continue;

            SQLConnector connector = SQLConnector.createSQLConnector(credential.host(), credential.port(), credential.user(), credential.password(), credential.database_name(), credential.type());
            assertFalse(connector.is_connected());
            ExecutionResult result = connector.execute(delete_command);
            assertTrue(connector.is_connected());
            assertEquals(1, result.getAffected_rows());
            connector.disconnect();
            assertFalse(connector.is_connected());
        }
    }

    @Test
    @Timeout(10000)
    @Tag("integration")
    public void testSimpleSQLConnectorWithUpdate() {
        MockCommandGenerator generator = new MockCommandGenerator();
        Command update_command = generator.update("students", new HashMap<>() {
            {
                put("age", 24);
                put("gpa", 3.98);
            }
        }).where(
                and(
                        equal("name", "Ünal"),
                        equal("surname", "Dalkılıç")
                )
        );

        for (ConnectionTestCredential credential : credentials) {
            if (credential.type().get_category() != DatabaseCategory.SQL) continue;

            SQLConnector connector = SQLConnector.createSQLConnector(credential.host(), credential.port(), credential.user(), credential.password(), credential.database_name(), credential.type());
            assertFalse(connector.is_connected());
            ExecutionResult result = connector.execute(update_command);
            assertTrue(connector.is_connected());
            assertEquals(1, result.getAffected_rows());
            connector.disconnect();
            assertFalse(connector.is_connected());
        }
    }
}
