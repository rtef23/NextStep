package util;

import java.util.regex.Pattern;

/**
 * Created by NAVER on 2017-11-13.
 */
public class RegularExpressionUtil {
    private RegularExpressionUtil() {
    }

    public static boolean isMatch(String regExp, String target) {
        return Pattern.matches(regExp, target);
    }
}
