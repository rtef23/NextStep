package core.mvc;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private LegacyHandlerMapping rm;
	private AnnotationHandlerMapping annotationHandlerMapping;

	@Override
	public void init() throws ServletException {
		rm = new LegacyHandlerMapping();
		rm.initMapping();

		annotationHandlerMapping = new AnnotationHandlerMapping("next.controller");
		annotationHandlerMapping.initialize();
	}

	private Object getHandler(HttpServletRequest request) {
		Object result = rm.getHandler(request);

		if (result != null) {
			return result;
		}

		return annotationHandlerMapping.getHandler(request);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestUri = req.getRequestURI();
		logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);


		try {
			ModelAndView mav;

			Object handler = getHandler(req);

			if (handler instanceof Controller) {
				Controller controller = (Controller) handler;

				mav = controller.execute(req, resp);
			} else if (handler instanceof HandlerExecution) {
				HandlerExecution execution = annotationHandlerMapping.getHandler(req);

				mav = execution.handle(req, resp);
			} else {
				throw new ServletException("No Handler Found");
			}

			View view = mav.getView();
			view.render(mav.getModel(), req, resp);
		} catch (Throwable e) {
			logger.error("Exception : {}", e);
			throw new ServletException(e.getMessage());
		}
	}
}
