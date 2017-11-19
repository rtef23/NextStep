/**
 * 
 */
package controller;

import java.io.IOException;

import db.DataBase;
import model.AbstractController;
import model.MyHttpRequest;
import model.MyHttpResponse;
import model.User;
import util.MyStringUtils;

/**
 * @author 송주용
 *
 */
public class UserCreateController extends AbstractController {

	/* (non-Javadoc)
	 * @see model.AbstractController#doGet(model.MyHttpRequest, model.MyHttpResponse)
	 */
	@Override
	public void doGet(MyHttpRequest request, MyHttpResponse response) throws IOException {
		DataBase.addUser(parseUser(request.getParameter("userId"), request.getParameter("password"),
			request.getParameter("name"), request.getParameter("email")));

		response.sendRedirect("/index.html");
	}

	/* (non-Javadoc)
	 * @see model.AbstractController#doPost(model.MyHttpRequest, model.MyHttpResponse)
	 */
	@Override
	public void doPost(MyHttpRequest request, MyHttpResponse response) throws IOException {
		DataBase.addUser(parseUser(request.getParameter("userId"), request.getParameter("password"),
			request.getParameter("name"), request.getParameter("email")));

		response.sendRedirect("/index.html");
	}

	private User parseUser(String id, String password, String name, String email) throws IllegalArgumentException {
		if (MyStringUtils.isAnyEmpty(id, password, name, email)) {
			throw new IllegalArgumentException();
		}

		return new User(id, password, name, email);
	}
}
