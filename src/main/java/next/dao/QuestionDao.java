package next.dao;

import core.jdbc.refactoring.jdbctemplate.JdbcTemplate;
import core.jdbc.refactoring.result.ExecuteResult;
import next.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
	public List<Question> findAll() {
		JdbcTemplate<Void, List<Question>> jdbcTemplate = new JdbcTemplate<>();

		jdbcTemplate.setResultSetMapper((resultSet) -> {
			List<Question> result = new ArrayList<>();

			while (resultSet.next()) {
				Question question = new Question();

				question.setQuestionId(resultSet.getLong("questionId"));
				question.setWriter(resultSet.getString("writer"));
				question.setTitle(resultSet.getString("title"));
				question.setContents(resultSet.getString("contents"));
				question.setCountOfAnswer(resultSet.getInt("countOfAnswer"));
				question.setCreatedDate(resultSet.getDate("createdDate"));

				result.add(question);
			}

			return result;
		});

		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM Questions";
		ExecuteResult<Void, List<Question>> result = jdbcTemplate.execute(sql, null);

		return result.getQueryResult();
	}

	public Question findOne(Long questionId) {
		JdbcTemplate<Long, Question> jdbcTemplate = new JdbcTemplate<>();

		jdbcTemplate.setResultSetMapper((resultSet) -> {
			Question question = new Question();

			if (resultSet.next() == false) {
				return null;
			}

			question.setQuestionId(resultSet.getLong("questionId"));
			question.setWriter(resultSet.getString("writer"));
			question.setTitle(resultSet.getString("title"));
			question.setContents(resultSet.getString("contents"));
			question.setCountOfAnswer(resultSet.getInt("countOfAnswer"));
			question.setCreatedDate(resultSet.getDate("createdDate"));

			return question;
		});

		jdbcTemplate.setPreparedStatementSetter(((preparedStatement, data) -> {
			preparedStatement.setLong(1, data);
		}));

		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM Questions WHERE questionId = ?";
		ExecuteResult<Long, Question> result = jdbcTemplate.execute(sql, questionId);

		return result.getQueryResult();
	}
}
