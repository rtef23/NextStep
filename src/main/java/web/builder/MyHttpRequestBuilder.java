/**
 * 
 */
package web.builder;

import web.enums.MyHttpMethod;
import web.model.MyHttpRequest;
import web.model.MyHttpRequestBody;
import web.model.MyHttpRequestHeader;
import web.parser.RequestParser;
import web.parser.RequestParserFactory;

/**
 * @author 송주용
 *
 */
public class MyHttpRequestBuilder {
	private MyHttpRequestBuilder() {}

	private static final String TITLE_SEPERATOR = " ";
	private static final String REQUEST_PART_SEPERATOR = "\r\n\r\n";
	private static final String LINE_SEPERATOR = "\r\n";

	public static MyHttpRequest build(String requestString) throws IllegalArgumentException {
		MyHttpRequest request = new MyHttpRequest();
		String[] dividedRequest = requestString.split(REQUEST_PART_SEPERATOR);

		int headerTitleIndex = dividedRequest[0].indexOf(LINE_SEPERATOR);

		if (headerTitleIndex < 0) {
			throw new IllegalArgumentException();
		}

		String headerTitle = dividedRequest[0].substring(0, headerTitleIndex);
		String headerProperties = dividedRequest[0].substring(headerTitleIndex + LINE_SEPERATOR.length(),
			dividedRequest[0].length());
		String[] headerTitleElements = headerTitle.split(TITLE_SEPERATOR);

		if (headerTitleElements.length != 3) {
			throw new IllegalArgumentException();
		}

		MyHttpRequestHeader header = new MyHttpRequestHeader();

		header.setHttpMethod(MyHttpMethod.classifyMethod(headerTitleElements[0]));
		header.setPath(headerTitleElements[1]);
		header.setProtocol(headerTitleElements[2]);

		RequestParser parser = RequestParserFactory.getParser(header.getHttpMethod());

		parser.parseHeader(header, headerProperties);

		MyHttpRequestBody body = parser.parseBody(dividedRequest[1]);

		request.setMyHttpRequestHeader(header);
		request.setMyHttpRequestBody(body);

		return request;
	}
}
