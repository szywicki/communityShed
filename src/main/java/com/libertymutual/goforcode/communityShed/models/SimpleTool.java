package com.libertymutual.goforcode.communityShed.models;

import java.sql.Date;

public class SimpleTool {
	
	private String toolName;
	private String toolDescription;
	private String category;
	private String manufacturer;
	private int toolAge;
	private String image;
	private Date dateCheckout;
	private Date dateReturn;
	
	
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getToolDescription() {
		return toolDescription;
	}
	public void setToolDescription(String toolDescription) {
		this.toolDescription = toolDescription;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public int getToolAge() {
		return toolAge;
	}
	public void setToolAge(int toolAge) {
		this.toolAge = toolAge;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getDateCheckout() {
		return dateCheckout;
	}
	public void setDateCheckout(Date dateCheckout) {
		this.dateCheckout = dateCheckout;
	}
	public Date getDateReturn() {
		return dateReturn;
	}
	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

}
