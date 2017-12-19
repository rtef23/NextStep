package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	public void showClass() {
		Class<Question> clazz = Question.class;

		logger.debug("class name : {}", clazz.getName());

		StringBuilder buffer = new StringBuilder();

		for (Field field : clazz.getDeclaredFields()) {
			buffer.append(String.format("[type : %s, name : %s]", field.getType(), field.getName()));
		}

		logger.debug("field : \n{}", buffer.toString());

		buffer = new StringBuilder();

		for (Constructor constructor : clazz.getDeclaredConstructors()) {
			buffer.append("[");

			for (Parameter parameter : constructor.getParameters()) {
				buffer.append(String.format("{type : %s, name : %s}", parameter.getType(), parameter.getName()));
			}

			buffer.append("]\n");
		}

		logger.debug("constructors : \n{}", buffer.toString());

		buffer = new StringBuilder();

		for (Method method : clazz.getDeclaredMethods()) {
			buffer.append("[");

			for (Parameter parameter : method.getParameters()) {
				buffer.append(String.format("{type : %s, name : %s}", parameter.getType(), parameter.getName()));
			}

			buffer.append("]\n");
		}

		logger.debug("methods : \n{}", buffer.toString());
	}

	@Test
	public void newInstanceWithConstructorArgs() throws Exception {
		Class<User> clazz = User.class;
		logger.debug(clazz.getName());

		User generatedUserClass = null;


		for (Constructor constructor : clazz.getConstructors()) {
			if (constructor.getParameters().length == 4) {
				Object id = new String("id");
				Object password = new String("password");
				Object name = new String("name");
				Object email = new String("email");
				generatedUserClass = (User) constructor.newInstance(id, password, name, email);
			}
		}

		Assert.assertNotNull(generatedUserClass);
	}

	@Test
	public void privateFieldAccess() throws Exception {
		String testString = "this is test value";
		int testInt = 40;

		Student generatedStudentClass = new Student();

		Class<Student> clazz = Student.class;
		logger.debug(clazz.getName());

		Field name = clazz.getDeclaredField("name");
		name.setAccessible(true);
		name.set(generatedStudentClass, testString);

		Field age = clazz.getDeclaredField("age");
		age.setAccessible(true);
		age.setInt(generatedStudentClass, testInt);

		Assert.assertEquals(testString, generatedStudentClass.getName());
		Assert.assertEquals(testInt, generatedStudentClass.getAge());
	}
}
