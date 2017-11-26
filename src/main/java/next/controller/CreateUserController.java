package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import core.web.controller.AbstractController;
import next.model.User;

public class CreateUserController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

	/* (non-Javadoc)
	 * @see core.web.controller.AbstractController#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String get(HttpServletRequest request, HttpServletResponse response) {
		return "user/form";
	}

	/* (non-Javadoc)
	 * @see core.web.controller.AbstractController#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String post(HttpServletRequest request, HttpServletResponse response) {
		User user = new User(request.getParameter("userId"), request.getParameter("password"),
			request.getParameter("name"),
			request.getParameter("email"));
		log.debug("User : {}", user);

		DataBase.addUser(user);

		return "redirect:/";
	}
}
