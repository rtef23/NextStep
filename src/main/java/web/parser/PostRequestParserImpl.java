/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

package web.parser;

import web.model.MyHttpRequestBody;
import web.model.MyHttpRequestHeader;

/**
 * @author 송주용
 */
public class PostRequestParserImpl implements RequestParser {

	@Override
	public void parseHeader(MyHttpRequestHeader header, String target) {
		
	}

	@Override
	public MyHttpRequestBody parseBody(String target) {
		return null;
	}
	
}
