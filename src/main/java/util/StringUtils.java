/**
 * 
 */
package util;

/**
 * @author 송주용
 *
 */
public class StringUtils {
	/**
	 * 
	 */
	public StringUtils() {}

	public static boolean isEmpty(String target) {
		if (target == null || target.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
