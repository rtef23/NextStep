package next.controller;

import core.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QnAFormController implements Controller {
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
		return "form.jsp";
	}

	private String doPost(HttpServletRequest req, HttpServletResponse resp) {
		return "form.jsp";
	}
}
