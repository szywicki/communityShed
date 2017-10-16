package com.libertymutual.goforcode.communityShed.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libertymutual.goforcode.communityShed.services.MailGunEmailService;


@Entity
@Table(name="shedder")
public abstract class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

		
	@Column(unique=true, length=255, nullable=false)
	private String email;
		
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="users", cascade=CascadeType.ALL)
	private List<Group> groups;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="pendingUsers", cascade=CascadeType.ALL)
	private List<Group> pendingGroups;
	
	
	public User() {}
	
	public User(String email) {
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void addGroup(Group group) {
		group.addUserToGroup(this);
	}
	
	public void removeGroup (Group group) {
//		group.removeUserFromGroup(this);
	}
	
	public abstract List<Tool> getTools();

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}


	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public abstract void inviteToGroup(Group group, MailGunEmailService emailer);

	public List<Group> getPendingGroups() {
		return pendingGroups;
	}

	public void setPendingGroups(List<Group> pendingGroups) {
		this.pendingGroups = pendingGroups;
	}
}
