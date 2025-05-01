package org.unaldalkilic.connection;

import org.unaldalkilic.command.Command;
import org.unaldalkilic.util.execution_result.ExecutionResult;

public interface ConnectorInterface {
    public String connector_string();
    public boolean connect();
    public boolean is_connected();
    public boolean disconnect();
    public ExecutionResult execute(Command command);
}
