/**
 * 
 */
package model;

import java.util.Map;

/**
 * @author 송주용
 *
 */
public class mHttpRequest {
	private Map<String, String> header;
	private Map<String, String> body;

	/**
	 * @return the header
	 */
	public Map<String, String> getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	/**
	 * @return the body
	 */
	public Map<String, String> getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(Map<String, String> body) {
		this.body = body;
	}

}
