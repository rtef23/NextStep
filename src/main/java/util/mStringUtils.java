/**
 * 
 */
package util;

/**
 * @author 송주용
 *
 */
public class mStringUtils {
	private mStringUtils() {}

	public static boolean isAnyEmpty(String... strings) {
		if (strings == null) {
			return true;
		}

		for (String string : strings) {
			if (string == null || string.length() == 0) {
				return true;
			}
		}
		return false;
	}
}
