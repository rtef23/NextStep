/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

package web.model;

import java.util.Map;

/**
 * @author 송주용
 */
public class MyHttpRequest {
	private MyHttpRequestHeader myHttpRequestHeader;
	private MyHttpRequestBody myHttpRequestBody;

	public Map<String, String> getParameters() {
		return myHttpRequestHeader.getParameters();
	}

	public String getParameter(String key) {
		return myHttpRequestHeader.getParameter(key);
	}

	public void setMyHttpRequestHeader(MyHttpRequestHeader myHttpRequestHeader) {
		this.myHttpRequestHeader = myHttpRequestHeader;
	}

	public void setMyHttpRequestBody(MyHttpRequestBody myHttpRequestBody) {
		this.myHttpRequestBody = myHttpRequestBody;
	}

	public MyHttpRequestHeader getMyHttpRequestHeader() {
		return myHttpRequestHeader;
	}

	public MyHttpRequestBody getMyHttpRequestBody() {
		return myHttpRequestBody;
	}
}
