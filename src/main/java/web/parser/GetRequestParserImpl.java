/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

package web.parser;

import java.util.HashMap;
import java.util.Map;

import web.model.MyHttpRequestBody;
import web.model.MyHttpRequestHeader;

/**
 * @author 송주용
 */
public class GetRequestParserImpl implements RequestParser {
	private final String QUERY_SEPERATOR = "?";
	private final String PARAMETER_SEPERATOR = "&";
	private final String KEY_VALUE_SEPERATOR = "=";

	@Override
	public void parseHeader(MyHttpRequestHeader header, String target) {
		int seperatorIndex = header.getPath().indexOf(QUERY_SEPERATOR);

		if (seperatorIndex > 0) {
			String parameterString = header.getPath().substring(seperatorIndex + 1, header.getPath().length());
			header.setPath(target.replace(header.getPath(), parameterString));
			header.setParameters(parseParameter(parameterString));
		}

		parseProperties(header, target);
	}

	private Map<String, String> parseParameter(String path) {
		Map<String, String> resultParameters = new HashMap<>();
		String[] parameters = path.split(PARAMETER_SEPERATOR);

		for (String parameter : parameters) {
			String[] parameterElement = parameter.split(KEY_VALUE_SEPERATOR);
			resultParameters.put(parameterElement[0], parameterElement[1]);
		}

		return resultParameters;
	}

	private void parseProperties(MyHttpRequestHeader header, String properties) {

	}

	@Override
	public MyHttpRequestBody parseBody(String target) {
		return null;
	}

}
