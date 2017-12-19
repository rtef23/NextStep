package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit4TestRunner {
	@Test
	public void run() throws Exception {
		Class<Junit4Test> clazz = Junit4Test.class;

		for (Method method : clazz.getMethods()) {
			if (method.getAnnotation(MyTest.class) != null) {
				method.invoke(clazz.newInstance());
			}
		}
	}

	@Test
	public void run1() throws Exception {
		Class<Junit4Test> clazz = Junit4Test.class;

		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(MyTest.class)) {
				method.invoke(clazz.newInstance());
			}
		}
	}
}
