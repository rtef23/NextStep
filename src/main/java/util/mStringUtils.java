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

	public static boolean isEqual(String target1, String target2) {
		if (target1 == target2) {
			return true;
		}
		if (target1 == null || target2 == null) {
			return false;
		}
		return target1.equals(target2);
	}
}
