package com.libertymutual.goforcode.communityShed.models;

import java.net.URL;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="communityshed_tool")

public class Tool {
	@Id
	@GeneratedValue(generator = "ToolIdSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "ToolIdSeq", sequenceName = "ToolIdSeq")
	private Long id;

	
	@Column(nullable = false, length = 30)
	private String toolName;

	@Column(nullable = false, length = 80)
	private String toolDescription;

	@Column(nullable = true, length = 30)
	private String category;
	
	@Column(nullable = true, length = 30)
	private String brand;

	@Column(nullable = true)
	private Date dateCheckout;
	
	@Column(nullable = true)
	private Date dateReturn;
	
	@Column(nullable = true, length = 30)
	private String status;
	
	@Column(nullable = true)
	private int toolAge;
	
	@Column(nullable = true)
	private URL image;

	public Tool() {}

	public Tool(String toolName, String toolDescription, String category, String brand, Date dateCheckout, Date dateReturn, String status, int toolAge, URL image) {
		
		this.toolName = toolName;
		this.toolDescription = toolDescription;
		this.category = category;
		this.brand = brand;
		this.dateCheckout = dateCheckout;
		this.dateReturn = dateReturn;
		this.status = status;
		this.toolAge = toolAge;
		this.image = image;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public URL getImage() {
		return image;
	}

	public void setImage(URL image) {
		this.image = image;
	}

	public int getToolAge() {
		return toolAge;
	}

	public void setToolAge(int toolAge) {
		this.toolAge = toolAge;
	}


}
