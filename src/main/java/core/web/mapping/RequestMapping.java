/**
 * 
 */
package core.web.mapping;

import java.util.HashMap;
import java.util.Map;

import core.web.controller.AbstractController;

/**
 * @author 송주용
 *
 */
public class RequestMapping {
	private Map<String, AbstractController> controllers;

	private RequestMapping() {
		controllers = new HashMap<>();
	}

	private static final class LazyHolder {
		static RequestMapping instance = new RequestMapping();
	}

	public static void addController(String path, AbstractController controller) {
		LazyHolder.instance.controllers.put(path, controller);
	}

	public static AbstractController getController(String path) {
		return LazyHolder.instance.controllers.get(path);
	}
}
