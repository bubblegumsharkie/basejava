package org.resumebase.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {
    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T launchStatement(String statement, StatementExecutor<T> statementExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            return statementExecutor.execute(preparedStatement);
        } catch (SQLException e) {
            throw SQLExceptionHandler.convert(e);
        }
    }

}
