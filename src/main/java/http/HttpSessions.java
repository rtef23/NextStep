/**
 * 
 */
package http;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author 송주용
 *
 */
public class HttpSessions {
	/**
	 * 
	 */
	private HttpSessions() {
		sessions = Maps.newHashMap();
	}

	private Map<String, HttpSession> sessions;

	private final static class LazyHolder {
		final static HttpSessions instance = new HttpSessions();
	}

	public static HttpSessions getInstance() {
		return LazyHolder.instance;
	}

	public static HttpSession getSession(String sessionId) {
		return LazyHolder.instance.sessions.get(sessionId);
	}

	public static boolean isExistSessionId(String sessionId) {
		return LazyHolder.instance.sessions.containsKey(sessionId);
	}

	public static void addSession(String sessionId, HttpSession session) {
		LazyHolder.instance.sessions.put(sessionId, session);
	}
}
