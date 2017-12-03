package core.jdbc.refactoring.pssetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter<T> {
	void setValues(PreparedStatement preparedStatement, T data) throws SQLException;
}
