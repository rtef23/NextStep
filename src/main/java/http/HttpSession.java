/**
 * 
 */
package http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 송주용
 *
 */
public class HttpSession {
	private String id;
	Map<String, Object> attributes;

	/**
	 * 
	 */
	public HttpSession(String id) {
		this.id = id;
		attributes = new HashMap<>();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void addAttribute(String key, Object attribute) {
		attributes.put(key, attribute);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public void removeAttribute(String key) {
		attributes.remove(key);
	}

	public void invalidate() {
		attributes.clear();
	}
}
