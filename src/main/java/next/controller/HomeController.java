package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController extends AbstractController {
	private QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return jspView("home.jsp").addObject("questions", questionDao.findAll());
	}
}
