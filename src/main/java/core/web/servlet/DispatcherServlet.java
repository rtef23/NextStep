/**
 * 
 */
package core.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.controller.AbstractController;
import core.web.controller.ForwardController;
import core.web.mapping.RequestMapping;
import next.controller.CreateUserController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;

/**
 * @author 송주용
 *
 */

/**
 * loadOnStartup 옵션
 * 
 * 해당 옵션이 존재하는 경우,
 * 웹 어플리케이션을 웹 컨테이너에 등록시, 해당 서블릿 객체를 생성하고 초기화하는 작업을 수행하게 된다.
 * 
 */
@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private final String REDIRECT_PREFIX = "redirect:";
	private final String PREFIX = "/";
	private final String SUFFIX = ".jsp";

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		RequestMapping.addController("/", new HomeController());

		RequestMapping.addController("/users/form", new ForwardController("user/form"));
		RequestMapping.addController("/users/create", new CreateUserController());

		RequestMapping.addController("/users", new ListUserController());

		RequestMapping.addController("/users/loginForm", new ForwardController("user/login"));
		RequestMapping.addController("/users/login", new LoginController());

		RequestMapping.addController("/users/logout", new LogoutController());

		RequestMapping.addController("/users/profile", new ProfileController());

		RequestMapping.addController("/users/update", new UpdateUserController());
		RequestMapping.addController("/users/updateForm", new UpdateUserController());
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String requestURI = request.getRequestURI();

		AbstractController controller = RequestMapping.getController(requestURI);

		if (controller == null) {
			response.sendError(404);
			return;
		}

		try {
			String viewName = controller.execute(request, response);

			if (viewName == null) {
				response.sendError(404);
				return;
			}

			dispatch(viewName, request, response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServletException(e);
		}
	}

	private void dispatch(String viewName, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		if (viewName.startsWith(REDIRECT_PREFIX)) {
			response.sendRedirect(viewName.replace(REDIRECT_PREFIX, ""));
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(PREFIX + viewName + SUFFIX);
		dispatcher.forward(request, response);

	}
}
