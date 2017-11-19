/**
 * 
 */
package controller;

import java.io.IOException;

import model.AbstractController;
import model.MyHttpRequest;
import model.MyHttpResponse;

/**
 * @author 송주용
 *
 */
public class DefaultController extends AbstractController {

	/* (non-Javadoc)
	 * @see model.AbstractController#doGet(model.MyHttpRequest, model.MyHttpResponse)
	 */
	@Override
	public void doGet(MyHttpRequest request, MyHttpResponse response) throws IOException {
		String path = request.getPath();

		if (path.endsWith(".html")) {
			response.sendForward(path);
		} else if (path.endsWith(".js")) {
			response.sendForward(path);
		} else if (path.endsWith(".css")) {
			response.sendForward(path);
		}
		response.send404();
	}

	/* (non-Javadoc)
	 * @see model.AbstractController#doPost(model.MyHttpRequest, model.MyHttpResponse)
	 */
	@Override
	public void doPost(MyHttpRequest request, MyHttpResponse response) throws IOException {
		String path = request.getPath();

		if (path.endsWith(".html")) {
			response.sendForward(path);
		} else if (path.endsWith(".js")) {
			response.sendForward(path);
		} else if (path.endsWith(".css")) {
			response.sendForward(path);
		}
		response.send404();
	}

}
