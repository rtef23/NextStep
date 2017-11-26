package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.web.controller.AbstractController;

public class LogoutController extends AbstractController {

	@Override
	protected String get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
}
