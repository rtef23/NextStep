/**
 * 
 */
package webserver;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import model.MyHttpResponse;
import util.FileUtil;

/**
 * @author 송주용
 *
 */
public class HttpResponseTest {
	private final String testDirectory = "./src/test/resources";

	@Test
	public void responseForward() throws Exception {
		//given
		String realFileName = "/index.html";
		String fileName = "Http_Forward.txt";
		FileOutputStream outputStream = new FileOutputStream(new File(testDirectory + fileName));

		//when
		MyHttpResponse response = new MyHttpResponse(outputStream);
		response.sendForward(realFileName);

		//then
		byte[] responseFileContents = FileUtil.readFile(testDirectory + fileName);
		byte[] realFileContents = FileUtil.readFile("webapp" + realFileName);

		assertTrue(new String(responseFileContents).contains(new String(realFileContents)));

	}
}
