package next.dao;

import core.jdbc.refactoring.jdbctemplate.JdbcTemplate;
import next.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
	public void insert(User user) throws SQLException {
		JdbcTemplate<User, Void> jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.setPreparedStatementSetter(((preparedStatement, data) -> {
			preparedStatement.setString(1, data.getUserId());
			preparedStatement.setString(2, data.getPassword());
			preparedStatement.setString(3, data.getName());
			preparedStatement.setString(4, data.getEmail());
		}));

		jdbcTemplate.execute("INSERT INTO USERS VALUES (?, ?, ?, ?)", user);
	}

	public void update(User user) throws SQLException {
		JdbcTemplate<User, Void> jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.setPreparedStatementSetter((preparedStatement, data) -> {
			preparedStatement.setString(1, data.getPassword());
			preparedStatement.setString(2, data.getName());
			preparedStatement.setString(3, data.getEmail());
			preparedStatement.setString(4, data.getUserId());
		});

		jdbcTemplate.execute("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?", user);
	}

	public List<User> findAll() throws SQLException {
		JdbcTemplate<Void, List<User>> jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.setResultSetMapper((resultSet -> {
			List<User> result = new ArrayList<>();

			while (resultSet.next()) {
				result.add(new User(
						resultSet.getString("userId"),
						resultSet.getString("password"),
						resultSet.getString("name"),
						resultSet.getString("email")
				));
			}

			return result;
		}));

		return jdbcTemplate.execute("SELECT userId, password, name, email FROM users", null).getQueryResult();
	}

	public User findByUserId(String userId) throws SQLException {
		JdbcTemplate<String, User> jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.setPreparedStatementSetter((preparedStatement, data) -> {
			preparedStatement.setString(1, data);
		});

		jdbcTemplate.setResultSetMapper((resultSet -> {
			if (resultSet.next() == false) {
				return null;
			}

			return new User(
					resultSet.getString("userId"),
					resultSet.getString("password"),
					resultSet.getString("name"),
					resultSet.getString("email")
			);
		}));

		return jdbcTemplate.execute("SELECT userId, password, name, email FROM USERS WHERE userid=?", userId).getQueryResult();
	}
}
