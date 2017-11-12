/**
 * 
 */
package web.manager;

import java.util.HashMap;
import java.util.Map;

import web.model.Controller;
import web.model.MappingInformation;

/**
 * @author 송주용
 *
 */
public class ControllerManager {
	private Map<MappingInformation, Controller> mappingControllers;
	private static ControllerManager instance;

	private ControllerManager() {}

	public static ControllerManager getInstance() {
		if (instance == null) {
			instance = new ControllerManager();

			instance.mappingControllers = new HashMap<>();
		}

		return instance;
	}

	public void addController(MappingInformation information, Controller controller) {
		mappingControllers.put(information, controller);
	}

	public Controller getController(MappingInformation information) {
		return mappingControllers.get(information);
	}
}
