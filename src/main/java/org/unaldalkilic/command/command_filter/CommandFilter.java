package org.unaldalkilic.command.command_filter;

public class CommandFilter {
    private final String field;
    private final CommandOperator operator;
    private final Object value;

    public CommandFilter(String field, CommandOperator operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() { return field; }
    public CommandOperator getOperator() { return operator; }
    public Object getValue() { return value; }
}
