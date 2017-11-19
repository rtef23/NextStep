/**
 * 
 */
package model;

/**
 * @author 송주용
 *
 */
public class MyCookie {
	private String key;
	private String value;

	/**
	 * 
	 */
	public MyCookie() {
	}
	
	
	/**
	 * @param key
	 * @param value
	 */
	public MyCookie(String key, String value) {
		this.key = key;
		this.value = value;
	}


	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
