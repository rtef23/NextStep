/**
 * 
 */
package controller;

import java.io.IOException;
import java.util.Collection;

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
public class UserListController extends AbstractController {
	/* (non-Javadoc)
	 * @see model.AbstractController#doGet(model.MyHttpRequest, model.MyHttpResponse)
	 */
	@Override
	public void doGet(MyHttpRequest request, MyHttpResponse response) throws IOException {
		String isLogined = request.getCookie("logined");

		if (MyStringUtils.isAnyEmpty(isLogined)) {
			response.sendRedirect("/index.html");
			return;
		}

		if (MyStringUtils.isEqual("true", isLogined) == false) {
			response.sendRedirect("/index.html");
			return;
		}

		Collection<User> users = DataBase.findAll();
		StringBuilder buffer = new StringBuilder();

		buffer.append("<table>");

		for (User user : users) {
			buffer.append("<tr>");

			buffer.append("<td>" + user.getUserId() + "</td>");
			buffer.append("<td>" + user.getName() + "</td>");
			buffer.append("<td>" + user.getEmail() + "</td>");

			buffer.append("</tr>");
		}

		buffer.append("</table>");

		byte[] bufferByte = buffer.toString().getBytes();

		response.sendForward(bufferByte);
	}

	/* (non-Javadoc)
		 * @see model.AbstractController#doPost(model.MyHttpRequest, model.MyHttpResponse)
		 */
	@Override
	public void doPost(MyHttpRequest request, MyHttpResponse response) throws IOException {
		response.send404();
	}
}
