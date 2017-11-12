/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

package web.parser;

import web.enums.MyHttpMethod;

/**
 * @author 송주용
 */
public class RequestParserFactory {
	private RequestParserFactory() {
	}

	public static RequestParser getParser(MyHttpMethod method) throws IllegalArgumentException {
		switch (method) {
			case GET:
				return new GetRequestParserImpl();
			case POST:
			case PUT:
			case DELETE:
				return new PostRequestParserImpl();
			default:
				throw new IllegalArgumentException();
		}
	}
}
