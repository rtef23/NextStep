package web.parser;

import web.model.MyHttpRequestBody;
import web.model.MyHttpRequestHeader;

/**
 * @author 송주용
 */
public interface RequestParser {
	void parseHeader(MyHttpRequestHeader header, String target);
	MyHttpRequestBody parseBody(String target);
}
