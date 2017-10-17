package com.libertymutual.goforcode.communityShed.models;

import java.sql.Date;

public class SimpleTool {
	
	private String toolName;
	private String toolDescription;
	private String category;
	private String manufacturer;
	private int toolAge;
	
	
	
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
}
