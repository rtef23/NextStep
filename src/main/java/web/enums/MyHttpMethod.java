package web.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 송주용
 */
public enum MyHttpMethod {
	GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

	private String stringMethodName;

	MyHttpMethod(String methodName) {
		this.stringMethodName = methodName;
	}

	public static MyHttpMethod classifyMethod(String methodName) throws IllegalArgumentException {
		String upperMethodName = methodName.toUpperCase();

		for (MyHttpMethod method : values()) {
			if (StringUtils.equals(method.getStringMethodName(), upperMethodName)) {
				return method;
			}
		}

		throw new IllegalArgumentException("this server supports GET, POST, PUT, DELETE");
	}

	public String getStringMethodName() {
		return stringMethodName;
	}

	public void setStringMethodName(String stringMethodName) {
		this.stringMethodName = stringMethodName;
	}
}
