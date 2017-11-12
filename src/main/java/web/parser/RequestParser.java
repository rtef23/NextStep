package web.parser;

/**
 * @author 송주용
 */
public interface RequestParser {
	void parse(String target);
	void parse(String[] targets);
}
