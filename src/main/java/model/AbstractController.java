/**
 * 
 */
package model;

import java.io.IOException;

/**
 * @author 송주용
 *
 */
public abstract class AbstractController implements Controller {
	private final String GET = "GET";
	private final String POST = "POST";

	/* (non-Javadoc)
	 * @see model.Controller#service(model.MyHttpRequest, model.MyHttpResponse)
	 */
	@Override
	public void service(MyHttpRequest request, MyHttpResponse response) throws IOException {
		if (GET.equals(request.getMethod())) {
			doGet(request, response);
		} else if (POST.equals(request.getMethod())) {
			doPost(request, response);
		} else {
			response.send400();
		}
	}

	abstract public void doGet(MyHttpRequest request, MyHttpResponse response) throws IOException;

	abstract public void doPost(MyHttpRequest request, MyHttpResponse response) throws IOException;
}
