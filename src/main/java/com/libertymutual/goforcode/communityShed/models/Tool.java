package com.libertymutual.goforcode.communityShed.models;

import java.net.URL;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity

public class Tool {
	@Id
	@GeneratedValue(generator = "ToolIdSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "ToolIdSeq", sequenceName = "ToolIdSeq")
	private Long id;

	
	@Column(nullable = false, length = 30)
	private String tool_name;

	@Column(nullable = false, length = 50)
	private String tool_description;

	@Column(nullable = true, length = 10)
	private String category;
	
	@Column(nullable = true, length = 30)
	private String brand;

	@Column(nullable = true)
	private Date date_checkout;
	
	@Column(nullable = true)
	private Date date_return;
	
	@Column(nullable = true, length = 10)
	private Date status;
	
	@Column(nullable = true)
	private URL image;

	public Tool() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTool_name() {
		return tool_name;
	}

	public void setTool_name(String tool_name) {
		this.tool_name = tool_name;
	}

	public String getTool_description() {
		return tool_description;
	}

	public void setTool_description(String tool_description) {
		this.tool_description = tool_description;
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

	public Date getDate_checkout() {
		return date_checkout;
	}

	public void setDate_checkout(Date date_checkout) {
		this.date_checkout = date_checkout;
	}

	public Date getDate_return() {
		return date_return;
	}

	public void setDate_return(Date date_return) {
		this.date_return = date_return;
	}

	public Date getStatus() {
		return status;
	}

	public void setStatus(Date status) {
		this.status = status;
	}

	public URL getImage() {
		return image;
	}

	public void setImage(URL image) {
		this.image = image;
	}
}
