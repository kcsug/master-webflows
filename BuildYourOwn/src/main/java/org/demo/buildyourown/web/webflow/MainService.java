package org.demo.buildyourown.web.webflow;

import javax.servlet.http.HttpServletRequest;

import org.demo.buildyourown.web.beans.Cart;
import org.demo.buildyourown.web.beans.Phone;
import org.demo.buildyourown.web.webflow.command.Choose;
import org.springframework.stereotype.Service;
import org.springframework.webflow.context.ExternalContext;
@Service
public class MainService {
	
	public Phone makePhone(Choose choose){
		Phone phone = new Phone();
		phone.setType(choose.getType());
		return phone;
	}
	public Phone makeStdPhone(){
		Phone phone = new Phone();
		phone.setType("stdphone");
		return phone;
	}
	public String nextFlowName(Phone phone){
		switch (phone.getType()) {
		case "stdphone":
			return "stdphone";

		default:
			return "cordlessphone";
		}
	}
	
	public void addPrice(Phone phone){
		phone.setPrice(999.99);
	}
	public void addToCart(ExternalContext external, Phone phone){
		HttpServletRequest request = (HttpServletRequest)external.getNativeRequest();
		Cart cart =(Cart)request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
		}
		cart.addPhone(phone);
	}
}
