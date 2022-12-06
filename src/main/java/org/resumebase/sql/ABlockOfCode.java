package org.resumebase.sql;

import org.resumebase.model.Resume;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ABlockOfCode {
    Resume execute(PreparedStatement preparedStatement) throws SQLException;

}

