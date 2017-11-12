/**
 * 
 */
package web.model;

import web.enums.MyHttpMethod;

/**
 * @author 송주용
 *
 */
public class MappingInformation {
	private MyHttpMethod method;
	private String urlPath;

	public MyHttpMethod getMethod() {
		return method;
	}

	public void setMethod(MyHttpMethod method) {
		this.method = method;
	}
	
	public String getUrlPath() {
		return urlPath;
	}
	
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
}
