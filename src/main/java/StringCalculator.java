/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 송주용
 */
public class StringCalculator {
	private final int SEPERATOR_INDEX = 0;
	private final int NUMBER_INDEX = 1;
	private final String CUSTOM_SEPERATOR_STARTER = "//";
	private final String CUSTOM_SEPERATOR_END = "\n";
	private String[] defaultAddSeperators = {",", ":"};

	public String[] getDefaultAddSeperators() {
		return defaultAddSeperators;
	}

	public void setDefaultAddSeperators(String[] defaultAddSeperators) {
		this.defaultAddSeperators = defaultAddSeperators;
	}

	public int add(String expression) throws RuntimeException {
		String[] dividedExpressions = divideExpression(expression);

		if (StringUtils.isEmpty(dividedExpressions[NUMBER_INDEX])) {
			return 0;
		}

		String seperatorString = seperatorRegularExpression(dividedExpressions[SEPERATOR_INDEX]);
		String[] numbers = dividedExpressions[NUMBER_INDEX].split(seperatorString);

		int result = 0;

		for (String number : numbers) {
			int operand = Integer.parseInt(number);

			result = add(result, operand);
		}

		return result;
	}

	private String seperatorRegularExpression(String seperator) {
		if (StringUtils.isEmpty(seperator)) {
			return buildSeperatorString(defaultAddSeperators);
		}
		return buildSeperatorString(seperator);
	}

	private String buildSeperatorString(String[] seperators) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < defaultAddSeperators.length; i++) {
			builder.append(defaultAddSeperators[i]);

			if (i + 1 < defaultAddSeperators.length) {
				builder.append("|");
			}
		}

		return builder.toString();
	}

	private String buildSeperatorString(String seperatorString) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < seperatorString.length(); i++) {
			builder.append(seperatorString.charAt(i));

			if (i + 1 < seperatorString.length()) {
				builder.append("|");
			}
		}

		return builder.toString();
	}

	private String[] divideExpression(String expression) {
		if (StringUtils.contains(expression, CUSTOM_SEPERATOR_STARTER) == false || StringUtils.contains(expression, CUSTOM_SEPERATOR_END) == false) {
			return new String[] {"", expression};
		}
		Pattern pattern = Pattern.compile(String.format("^%s(.*)%s(.*)", CUSTOM_SEPERATOR_STARTER, CUSTOM_SEPERATOR_END));
		Matcher matcher = pattern.matcher(expression);

		if (matcher.find()) {
			return new String[] {
				matcher.group(1),
				matcher.group(2)
			};
		} else {
			return new String[] {"", expression};
		}
	}

	public int add(int prevNumber, int nextNumber) throws RuntimeException {
		if (prevNumber < 0 || nextNumber < 0) {
			throw new RuntimeException();
		}
		return prevNumber + nextNumber;
	}
}
