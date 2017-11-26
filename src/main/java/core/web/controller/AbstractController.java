/**
 * 
 */
package core.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 송주용
 *
 */
public abstract class AbstractController implements Controller {
	private final String GET = "GET";
	private final String POST = "POST";

	/* (non-Javadoc)
	 * @see next.controller.Controller#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		switch (request.getMethod()) {
			case GET:
				return get(request, response);
			case POST:
				return post(request, response);
			default:
				return null;
		}
	}

	protected String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	protected String post(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
}
