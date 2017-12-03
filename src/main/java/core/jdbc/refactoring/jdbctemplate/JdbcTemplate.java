/**
 *
 */
package core.jdbc.refactoring.jdbctemplate;

import core.jdbc.ConnectionManager;
import core.jdbc.refactoring.exception.MSQLException;
import core.jdbc.refactoring.pssetter.PreparedStatementSetter;
import core.jdbc.refactoring.result.ExecuteResult;
import core.jdbc.refactoring.rsmapper.ResultSetMapper;
import org.h2.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 송주용
 */
public class JdbcTemplate<T1, T2> {
	private boolean isResultSetMapperSettinged;
	private PreparedStatementSetter<T1> preparedStatementSetter;
	private ResultSetMapper<T2> resultSetMapper;

	public JdbcTemplate() {
		resultSetMapper = (resultSet -> null);

		preparedStatementSetter = ((preparedStatement, data) -> {
		});
		isResultSetMapperSettinged = false;
	}

	public PreparedStatementSetter<T1> getPreparedStatementSetter() {
		return preparedStatementSetter;
	}

	public void setPreparedStatementSetter(PreparedStatementSetter<T1> preparedStatementSetter) {
		this.preparedStatementSetter = preparedStatementSetter;
	}

	public ResultSetMapper<T2> getResultSetMapper() {
		return resultSetMapper;
	}

	public void setResultSetMapper(ResultSetMapper<T2> resultSetMapper) {
		isResultSetMapperSettinged = true;
		this.resultSetMapper = resultSetMapper;
	}

	public ExecuteResult<T1, T2> execute(String query, T1 data) throws MSQLException {
		if (StringUtils.isNullOrEmpty(query)) {
			throw new MSQLException("invalid query");
		}

		if (preparedStatementSetter == null || resultSetMapper == null) {
			throw new MSQLException("invalid configuration");
		}

		try {
			try (Connection connection = ConnectionManager.getConnection();
			     PreparedStatement preparedStatement = connection.prepareStatement(query)) {


				preparedStatementSetter.setValues(preparedStatement, data);

				ExecuteResult<T1, T2> result = new ExecuteResult<>();

				result.setQuery(query);
				result.setData(data);

				if (isResultSetMapperSettinged == false) {
					int effectedRowCount = preparedStatement.executeUpdate();

					result.setEffectedRowCount(effectedRowCount);

					ResultSet resultSet = preparedStatement.getGeneratedKeys();

					if (resultSet.next()) {
						result.setAutoGenerateId(resultSet.getLong(1));
					}

					return result;
				} else {
					try (ResultSet resultSet = preparedStatement.executeQuery()) {
						T2 queryResult = resultSetMapper.mappingStrategy(resultSet);

						result.setQueryResult(queryResult);

						return result;
					}
				}
			}
		} catch (SQLException e) {
			throw new MSQLException(e);
		}
	}
}
