package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;


public class AnnotationHandlerMapping {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

	private Object[] basePackages;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackages) {
		this.basePackages = basePackages;
	}

	public void initialize() {
		try {
			for (Object basePackage : basePackages) {
				Reflections reflections = new Reflections(basePackage);

				Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);

				for (Class<?> annotatedClass : annotatedClasses) {
					for (Method method : annotatedClass.getMethods()) {
						Annotation annotation = method.getAnnotation(RequestMapping.class);

						if (annotation != null) {
							method.setAccessible(true);

							RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
							HandlerExecution handlerExecution = new HandlerExecution(annotatedClass.newInstance(), method);

							handlerExecutions.put(generateHandlerKey(requestMapping), handlerExecution);
						}
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("error occur\n{}", e.getMessage());
		}
	}

	private HandlerKey generateHandlerKey(RequestMapping requestMapping) {
		return new HandlerKey(requestMapping.value(), requestMapping.method());
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
