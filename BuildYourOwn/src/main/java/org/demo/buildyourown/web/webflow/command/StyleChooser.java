package org.demo.buildyourown.web.webflow.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StyleChooser implements Serializable{

	private static final long serialVersionUID = 1L;
	private String style = "Rotary";
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getMessage(){
		if(style.equals("Rotary")){
			return "Rotary phones are cool.";
		}
		return "Push button phones are a fad.";
	}
	public List<String> getStyles(){
		List<String> styles = new ArrayList<String>();
		styles.add("Rotary");
		styles.add("Push Button");
		return styles;
	}
}
