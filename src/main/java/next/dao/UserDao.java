package next.dao;

import next.model.User;
import next.refactoring.jdbctemplate.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
	public void insert(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.setPreparedStatementSetter((preparedStatement -> {
			preparedStatement.setString(1, user.getUserId());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getEmail());
		}));

		jdbcTemplate.execute("INSERT INTO USERS VALUES (?, ?, ?, ?)");
	}

	public void update(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.setPreparedStatementSetter((preparedStatement -> {
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getUserId());
		}));

		jdbcTemplate.execute("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?");
	}

	public List<User> findAll() throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();

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

		return (List<User>) jdbcTemplate.execute("SELECT userId, password, name, email FROM users");
	}

	public User findByUserId(String userId) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.setPreparedStatementSetter((preparedStatement -> {
			preparedStatement.setString(1, userId);
		}));

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

		return (User) jdbcTemplate.execute("SELECT userId, password, name, email FROM USERS WHERE userid=?");
	}
}
