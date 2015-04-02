package org.demo.buildyourown.web.webflow.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.demo.buildyourown.web.ValidationUtils;
import org.springframework.binding.validation.ValidationContext;

public class ColorChooser implements Serializable {

	private static final long serialVersionUID = 1L;
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<String> getColors() {
		List<String> colors = new ArrayList<String>();
		colors.add("Green");
		colors.add("Blue");
		colors.add("Red");
		colors.add("Black");
		colors.add("White");
		colors.add("Pink");
		return colors;
	}
	
	public void validateChooseColor(ValidationContext context){
		if(color.equals("Pink")){
			ValidationUtils.addErrorMessage(context, "color", "Invalid: Pink don't kid me.  We don't sell pink phones.");
		}
	}
	

}
