/**
 *
 */
package next.refactoring.jdbctemplate;

import core.jdbc.ConnectionManager;
import next.refactoring.exception.MSQLException;
import next.refactoring.pssetter.PreparedStatementSetter;
import next.refactoring.rsmapper.ResultSetMapper;
import org.h2.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 송주용
 */
public class JdbcTemplate {
	private boolean isResultSetMapperSettinged;
	private PreparedStatementSetter preparedStatementSetter;
	private ResultSetMapper resultSetMapper;

	public JdbcTemplate() {
		resultSetMapper = (resultSet -> null);
		preparedStatementSetter = (preparedStatement -> {
		});
		isResultSetMapperSettinged = false;
	}

	public PreparedStatementSetter getPreparedStatementSetter() {
		return preparedStatementSetter;
	}

	public void setPreparedStatementSetter(PreparedStatementSetter preparedStatementSetter) {
		this.preparedStatementSetter = preparedStatementSetter;
	}

	public ResultSetMapper getResultSetMapper() {
		return resultSetMapper;
	}

	public void setResultSetMapper(ResultSetMapper resultSetMapper) {
		isResultSetMapperSettinged = true;
		this.resultSetMapper = resultSetMapper;
	}

	/**
	 * ResultSetMapper를 설정한 경우, SELECT쿼리로 인식하여
	 * ResultSetMapper에 설정된 동작대로 결과를 리턴한다.
	 * <p>
	 * ResultSetMapper를 설정하지 않은 경우, SELECT 외(INSERT, UPDATE, DELETE)의 쿼리로 인식하며
	 * 영향 받은 row 수를 리턴한다.
	 *
	 * @param query
	 * @return
	 * @throws MSQLException
	 */
	public Object execute(String query) throws MSQLException {
		if (StringUtils.isNullOrEmpty(query)) {
			throw new MSQLException("invalid query");
		}

		if (preparedStatementSetter == null || resultSetMapper == null) {
			throw new MSQLException("invalid configuration");
		}

		try {
			try (Connection connection = ConnectionManager.getConnection();
			     PreparedStatement preparedStatement = connection.prepareStatement(query)) {


				preparedStatementSetter.setValues(preparedStatement);

				if (isResultSetMapperSettinged == false) {
					return preparedStatement.executeUpdate();
				} else {
					try (ResultSet resultSet = preparedStatement.executeQuery()) {
						return resultSetMapper.mappingStrategy(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new MSQLException(e);
		}
	}
}
