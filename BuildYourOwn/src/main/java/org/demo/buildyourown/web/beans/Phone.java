package org.demo.buildyourown.web.beans;

import java.io.Serializable;

public class Phone implements Serializable {


	private static final long serialVersionUID = 1L;
	private String type;
	private String color;
	private String dial;
	private double price;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDial() {
		return dial;
	}
	public void setDial(String dial) {
		this.dial = dial;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
