package com.project.app.coredb;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ParameterBinder {

  void bind(PreparedStatement preparedStatement) throws SQLException;

}
