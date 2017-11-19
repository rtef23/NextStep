/**
 * 
 */
package controller;

import java.io.IOException;
import java.util.Map;

import db.DataBase;
import model.AbstractController;
import model.MyHttpRequest;
import model.MyHttpResponse;
import model.User;
import model.UserLoginInfo;
import util.HttpRequestUtils;
import util.MyStringUtils;

/**
 * @author 송주용
 *
 */
public class UserLoginController extends AbstractController {
	/* (non-Javadoc)
	 * @see model.AbstractController#doGet(model.MyHttpRequest, model.MyHttpResponse)
	 */
	@Override
	public void doGet(MyHttpRequest request, MyHttpResponse response) throws IOException {
		response.send404();
	}

	/* (non-Javadoc)
		 * @see model.AbstractController#doPost(model.MyHttpRequest, model.MyHttpResponse)
		 */
	@Override
	public void doPost(MyHttpRequest request, MyHttpResponse response) throws IOException {
		UserLoginInfo requestUser = parseUserLoginInfo(request.getBody());
		User targetUser = DataBase.findUserById(requestUser.getUserId());

		if (targetUser == null || MyStringUtils.isEqual(requestUser.getPassword(), targetUser.getPassword()) == false) {
			response.addCookie("logined", "false");
			response.sendRedirect("/user/login_failed.html");

			return;
		}

		response.addCookie("logined", "true");
		response.sendRedirect("/index.html");
	}

	private UserLoginInfo parseUserLoginInfo(String string) {
		Map<String, String> userLoginString = HttpRequestUtils.parseValues(string, "&");

		String id = userLoginString.get("userId");
		String password = userLoginString.get("password");

		if (MyStringUtils.isAnyEmpty(id, password)) {
			throw new IllegalArgumentException();
		}

		return new UserLoginInfo(id, password);
	}
}
