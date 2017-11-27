/**
 * 
 */
package next.refactoring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;

/**
 * @author 송주용
 *
 */
abstract public class JdbcTemplate<T> {
	public void execute(String query) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			try {
				connection = ConnectionManager.getConnection();
				preparedStatement = connection.prepareStatement(query);

				setValue(preparedStatement);

				preparedStatement.executeUpdate();
			} finally {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	abstract protected void setValue(PreparedStatement preparedStatement) throws SQLException;

	abstract protected T mappingStrategy(ResultSet resultSet);
}
