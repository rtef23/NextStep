package next.dao;

import core.jdbc.refactoring.jdbctemplate.JdbcTemplate;
import core.jdbc.refactoring.result.ExecuteResult;
import next.model.Answer;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnswerDao {

	public void insert(Answer answer) {
		JdbcTemplate<Answer, Void> jdbcTemplate = new JdbcTemplate();

		jdbcTemplate.setPreparedStatementSetter(((preparedStatement, data) -> {
			FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd");

			preparedStatement.setString(1, data.getWriter());
			preparedStatement.setString(2, data.getContents());
			preparedStatement.setString(3, fastDateFormat.format(new Date()));
			preparedStatement.setLong(4, data.getQuestionId());
		}));

		ExecuteResult<Answer, Void> result = jdbcTemplate.execute("INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)", answer);

		answer.setAnswerId(result.getAutoGenerateId());
	}

	public List<Answer> findByQuestionId(Long questionId) {
		JdbcTemplate<Long, List<Answer>> jdbcTemplate = new JdbcTemplate<>();

		jdbcTemplate.setPreparedStatementSetter(((preparedStatement, data) -> {
			preparedStatement.setLong(1, data);
		}));

		jdbcTemplate.setResultSetMapper((resultSet -> {
			List<Answer> result = new ArrayList<>();

			while (resultSet.next()) {
				Answer answer = new Answer();

				answer.setAnswerId(resultSet.getLong("answerId"));
				answer.setWriter(resultSet.getString("writer"));
				answer.setContents(resultSet.getString("contents"));
				answer.setCreateDate(resultSet.getDate("createdDate"));
				answer.setQuestionId(resultSet.getLong("questionId"));

				result.add(answer);
			}

			return result;
		}));

		String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM Answers WHERE questionId = ?";
		ExecuteResult<Long, List<Answer>> result = jdbcTemplate.execute(sql, questionId);
		return result.getQueryResult();
	}

	public void delete(Long answerId) {
		JdbcTemplate<Long, Void> jdbcTemplate = new JdbcTemplate<>();

		jdbcTemplate.setPreparedStatementSetter(((preparedStatement, data) -> {
			preparedStatement.setLong(1, data);
		}));

		String sql = "DELETE FROM Answers WHERE answerId = ?";
		jdbcTemplate.execute(sql, answerId);
	}
}
