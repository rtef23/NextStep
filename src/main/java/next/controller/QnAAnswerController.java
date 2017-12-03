package next.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;
import org.h2.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class QnAAnswerController implements Controller {
	private String GET = "GET";
	private String POST = "POST";
	private String DELETE = "DELETE";

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (GET.equals(req.getMethod())) {
			return doGet(req, resp);
		}
		if (POST.equals(req.getMethod())) {
			return doPost(req, resp);
		}
		if (DELETE.equals(req.getMethod())) {
			return doDelete(req, resp);
		}
		throw new UnsupportedOperationException();
	}

	private String doGet(HttpServletRequest request, HttpServletResponse response) {
		throw new UnsupportedOperationException();
	}

	private String doPost(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper objectMapper = new ObjectMapper();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
			StringBuilder buffer = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line);
			}

			Answer answer = objectMapper.readValue(buffer.toString(), Answer.class);

			AnswerDao answerDao = new AnswerDao();

			answerDao.insert(answer);

			response.setContentType("application/json;charset=UTF-8");

			PrintWriter printWriter = response.getWriter();
			printWriter.print(objectMapper.writeValueAsString(answer));
			return null;
		} catch (IOException e) {
			throw new UnsupportedOperationException(e.getMessage());
		}
	}

	private String doDelete(HttpServletRequest request, HttpServletResponse response) {
		String answerIdParam = request.getParameter("aid");

		if (StringUtils.isNullOrEmpty(answerIdParam) || StringUtils.isNumber(answerIdParam) == false) {
			throw new UnsupportedOperationException();
		}
		Long answerId = Long.parseLong(answerIdParam);

		new AnswerDao().delete(answerId);

		try {
			response.setStatus(200);

			PrintWriter printWriter = response.getWriter();
			printWriter.print("");
		} catch (IOException e) {
			throw new UnsupportedOperationException(e.getMessage());
		}

		return null;
	}
}
