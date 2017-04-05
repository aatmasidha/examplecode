package com.ai.sample.db.type;

import java.io.Serializable;

public class MyJson  implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7626715579783187112L;
	private String stringProp;
	
/*	private Long longProp;*/

	public String getStringProp() {
		return stringProp;
	}

	public void setStringProp(String stringProp) {
		this.stringProp = stringProp;
	}

/*	public Long getLongProp() {
		return longProp;
	}

	public void setLongProp(Long longProp) {
		this.longProp = longProp;
	}
*/
}
