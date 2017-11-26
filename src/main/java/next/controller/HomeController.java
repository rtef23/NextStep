package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.web.controller.AbstractController;

public class HomeController extends AbstractController {
	/* (non-Javadoc)
	 * @see core.web.controller.AbstractController#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String get(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("users", DataBase.findAll());
		return "index";
	}
}
