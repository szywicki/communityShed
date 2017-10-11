package com.libertymutual.goforcode.communityShed.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="communityshed_group")

public class Group {
	@Id
	@GeneratedValue(generator = "GroupIdSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "GroupIdSeq", sequenceName = "GroupIdSeq")
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String groupName;

	@Column(nullable = false, length = 80)
	private String groupDescription;
	
	
	public Group() {}
	
	public Group (String groupName, String groupDescription) {
		this.groupName = groupName;
		this.groupDescription = groupDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	
	@OneToMany(mappedBy = "group")
	private List<Tool> tools;
	
	@ManyToMany()
	private List<User> users;
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser (User user) {
		if (users == null) {
			users = new ArrayList<User>();
		}
		users.add(user);
	}

	public List<Tool> getTools() {
		return tools;
	}

	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}
	
	public void addTool (Tool tool) {
		if (tools == null) {
			tools = new ArrayList<Tool>();
		}
		tools.add(tool);
	}
}
