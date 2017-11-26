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
public class ForwardController extends AbstractController {
	private String forwardUrl;

	public ForwardController(String forwardUrl) {
		if (forwardUrl == null) {
			throw new NullPointerException("forward 할 URL이 필요합니다.");
		}
		this.forwardUrl = forwardUrl;
	}

	/* (non-Javadoc)
	 * @see core.web.controller.AbstractController#get(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forwardUrl;
	}
}
