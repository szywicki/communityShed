package com.libertymutual.goforcode.communityShed.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

	
	@Column(nullable = false, length = 50)
	private String toolName;

	@Column(nullable = false, length = 255)
	private String toolDescription;

	@Column(nullable = true, length = 30)
	private String category;
	
	@Column(nullable = true, length = 30)
	private String manufacturer;

	//Checkout and return are really attributes of request.  Should we store dataAvailable here and populate it with the next date the item is available for checkout?
	@Column(nullable = true)
	private Date dateCheckout;
	
	@Column(nullable = true)
	private Date dateReturn;
	
	@Column(nullable = true, length = 30)
	private String status;
	
	@Column(nullable = true)
	private int toolAge;
	
	@Column(nullable = true)
	private String image;
	
	@ManyToOne
	private ConfirmedUser owner;
	
	@OneToMany(mappedBy="tool", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Request> requests;

	public Tool() {}

	public Tool(String toolName, String toolDescription, String category, String manufacturer, Date dateCheckout, Date dateReturn, String status, int toolAge, String image, ConfirmedUser owner) {
		
		this.toolName = toolName;
		this.toolDescription = toolDescription;
		this.category = category;
		this.manufacturer = manufacturer;
		this.dateCheckout = dateCheckout;
		this.dateReturn = dateReturn;
		this.status = status;
		this.toolAge = toolAge;
		this.image = image;
		this.owner = owner; 

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getToolAge() {
		return toolAge;
	}

	public void setToolAge(int toolAge) {
		this.toolAge = toolAge;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@ManyToOne()
	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public ConfirmedUser getOwner() {
		return owner;
	}

	public void setOwner(ConfirmedUser owner) {
		this.owner = owner;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	
	public void copyFromSimpleTool(SimpleTool simple)	{
		this.toolName = simple.getToolName();
		this.toolDescription = simple.getToolDescription();
		this.category = simple.getCategory();
		this.manufacturer = simple.getManufacturer();
		this.toolAge = simple.getToolAge();
		this.image = simple.getImage();
	}

}
