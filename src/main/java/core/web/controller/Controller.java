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
public interface Controller {
	String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
