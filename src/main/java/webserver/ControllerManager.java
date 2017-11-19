/**
 * 
 */
package webserver;

import java.util.Map;

import com.google.common.collect.Maps;

import model.AbstractController;

/**
 * @author 송주용
 *
 */
public class ControllerManager {
	private static ControllerManager instance;
	private static Map<String, AbstractController> controllers;

	private ControllerManager() {
		controllers = Maps.newHashMap();
	}

	public static ControllerManager getInstance() {
		if (instance == null) {
			instance = new ControllerManager();
		}

		return instance;
	}

	public static AbstractController getController(String path) {
		return controllers.get(path);
	}

	public static void addController(String path, AbstractController controller) {
		controllers.put(path, controller);
	}
}
