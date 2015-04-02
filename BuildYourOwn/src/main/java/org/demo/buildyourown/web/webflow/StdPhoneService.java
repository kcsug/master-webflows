package org.demo.buildyourown.web.webflow;

import org.demo.buildyourown.web.beans.Phone;
import org.demo.buildyourown.web.webflow.command.ColorChooser;
import org.demo.buildyourown.web.webflow.command.StyleChooser;
import org.springframework.stereotype.Service;

@Service
public class StdPhoneService {
	public ColorChooser newColorChooser(){
		ColorChooser chooser = new ColorChooser();
		chooser.setColor("Red");
		return chooser;
	}
	
	public void addType(Phone phone){
		phone.setType("Standard");
	}
	public void addColor(Phone phone, ColorChooser color){
		phone.setColor(color.getColor());
	}
	
	public void addStyle(Phone phone, StyleChooser style){
		phone.setDial(style.getStyle());
	}
}
