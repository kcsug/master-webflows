package org.demo.buildyourown.web.webflow.command;

import java.io.Serializable;

public class Choose implements Serializable{

	private static final long serialVersionUID = 1L;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
