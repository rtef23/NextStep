/**
 * 
 */
package model;

import java.io.IOException;

/**
 * @author 송주용
 *
 */
public interface Controller {
	void service(MyHttpRequest request, MyHttpResponse response) throws IOException;
}
