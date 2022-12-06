package org.resumebase.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ABlockOfCode {
    void execute(PreparedStatement preparedStatement) throws SQLException;

}

