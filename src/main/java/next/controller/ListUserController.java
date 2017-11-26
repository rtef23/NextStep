package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.web.controller.AbstractController;

public class ListUserController extends AbstractController {
	@Override
	protected String get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return "redirect:/users/loginForm";
		}

		req.setAttribute("users", DataBase.findAll());

		return "user/list";
	}
}
