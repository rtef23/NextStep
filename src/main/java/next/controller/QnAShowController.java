package next.controller;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import org.h2.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class QnAShowController implements Controller {
	private String GET = "GET";
	private String POST = "POST";

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (GET.equals(req.getMethod())) {
			return doGet(req, resp);
		}
		if (POST.equals(req.getMethod())) {
			return doPost(req, resp);
		}

		throw new UnsupportedOperationException();
	}

	private String doGet(HttpServletRequest req, HttpServletResponse resp) {
		String questionIdParam = req.getParameter("qid");

		if (StringUtils.isNullOrEmpty(questionIdParam) || StringUtils.isNumber(questionIdParam) == false) {
			throw new IllegalArgumentException();
		}

		Long questionId = Long.parseLong(questionIdParam);
		List<Answer> answerList = new AnswerDao().findByQuestionId(questionId);

		System.err.println(answerList);

		req.setAttribute("question", new QuestionDao().findOne(questionId));
		req.setAttribute("answers", answerList);

		return "show.jsp";
	}

	private String doPost(HttpServletRequest req, HttpServletResponse resp) {
		return "show.jsp";
	}
}
