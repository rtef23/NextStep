/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

package web.model;

import web.parser.RequestParser;
import web.parser.RequestParserFactory;

/**
 * @author 송주용
 */
public class MyHttpRequest {
	private MyHttpRequestHeader myHttpRequestHeader;
	private MyHttpRequestBody myHttpRequestBody;

	private MyHttpRequest() {
	}

	public static MyHttpRequest build(String requestString) throws IllegalArgumentException {
		MyHttpRequest request = new MyHttpRequest();
		String[] dividedRequest = requestString.split("\r\n\r\n");

		request.setMyHttpRequestHeader(MyHttpRequestHeader.parseHeader(dividedRequest[0]));
		RequestParser parser = RequestParserFactory.getParser(request.getMyHttpRequestHeader().getHttpMethod());

		parser.parse(dividedRequest);

		return request;
	}

	private void setMyHttpRequestHeader(MyHttpRequestHeader myHttpRequestHeader) {
		this.myHttpRequestHeader = myHttpRequestHeader;
	}

	private void setMyHttpRequestBody(MyHttpRequestBody myHttpRequestBody) {
		this.myHttpRequestBody = myHttpRequestBody;
	}

	public MyHttpRequestHeader getMyHttpRequestHeader() {
		return myHttpRequestHeader;
	}

	public MyHttpRequestBody getMyHttpRequestBody() {
		return myHttpRequestBody;
	}
}
