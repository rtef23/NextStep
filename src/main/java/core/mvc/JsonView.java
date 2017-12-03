package core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonView implements View {
	private Object model;

	public JsonView(Object model) {
		this.model = model;
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter printWriter = response.getWriter();

		ObjectMapper objectMapper = new ObjectMapper();
		printWriter.print(objectMapper.writeValueAsString(model));
	}
}
