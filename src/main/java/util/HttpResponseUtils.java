/**
 * 
 */
package util;

/**
 * @author 송주용
 *
 */
public class HttpResponseUtils {
	private HttpResponseUtils() {}

	public static String responseStatusString(int statusCode) {
		switch (statusCode) {
			case 200:
				return "OK";
			case 400:
				return "Bad Request";
			case 500:
				return "Internal Server Error";
			default:
				return "";
		}
	}
}
