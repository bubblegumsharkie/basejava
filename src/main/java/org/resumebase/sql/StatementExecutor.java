package org.resumebase.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementExecutor {
    void execute(PreparedStatement preparedStatement) throws SQLException;

}

