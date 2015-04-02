package org.demo.buildyourown.web.beans;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<Phone> phones = new ArrayList<Phone>();
	
	
	public List<Phone> getPhones(){
		return phones;
	}
	public void addPhone(Phone phone){
		phones.add(phone);
	}
}
