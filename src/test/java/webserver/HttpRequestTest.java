/**
 * 
 */
package webserver;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import model.MyHttpRequest;

/**
 * @author 송주용
 *
 */
public class HttpRequestTest {
	private final String testDirectory = "./src/test/resources/";

	@Test
	public void request_GET() throws Exception {
		//given
		String resourceFileName = "Http_GET.txt";
		InputStream inputStream = new FileInputStream(new File(testDirectory + resourceFileName));

		//when
		MyHttpRequest request = new MyHttpRequest(inputStream);

		//then
		assertEquals("GET", request.getMethod());
		assertEquals("/user/create", request.getPath());
		assertEquals("keep-alive", request.getHeader("Connection"));
		assertEquals("testValue", request.getParameter("testKey"));
		assertEquals("true", request.getCookie("logined"));
	}

	@Test
	public void request_POST() throws Exception {
		//given
		String resourceFileName = "Http_POST.txt";
		InputStream inputStream = new FileInputStream(new File(testDirectory + resourceFileName));

		//when
		MyHttpRequest request = new MyHttpRequest(inputStream);

		//then
		assertEquals("POST", request.getMethod());
		assertEquals("/user/create", request.getPath());
		assertEquals("keep-alive", request.getHeader("Connection"));
		assertEquals("testValue1", request.getParameter("testKey1"));
		assertEquals("true", request.getCookie("logined"));
	}
}
