package com.libertymutual.goforcode.communityShed.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.libertymutual.goforcode.communityShed.services.MailGunEmailService;
import com.mashape.unirest.http.exceptions.UnirestException;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class, 
		property = "id"
)
@Entity
public class ConfirmedUser extends User {
	
	private static final long serialVersionUID = 1L;

	@Column(length=75)
	private String firstName;
		
	@Column(length=75)
	private String lastName;

	@Column(length=255)
	private String password;

	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
	private List<Tool> tools;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="borrower", cascade=CascadeType.ALL)
	private List<Request> requestsMade;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="loaner", cascade=CascadeType.ALL)
	private List<Request> requestsReceived;

	public ConfirmedUser() {}
	
	public ConfirmedUser(String password, String email, String firstName, String lastName) {
		super(email);
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Tool> getTools() {
		return tools;
	}

	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}

	public List<Request> getRequestsMade() {
		return requestsMade;
	}

	public void setRequestsMade(List<Request> requestsMade) {
		this.requestsMade = requestsMade;
	}

	public List<Request> getRequestsReceived() {
		return requestsReceived;
	}

	public void setRequestsReceived(List<Request> requestsReceived) {
		this.requestsReceived = requestsReceived;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ConfirmedUser) {
			ConfirmedUser u = (ConfirmedUser) o;
			return u.getId() == getId();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if (getId() == null) {
			return 0;
		}
		return getId().hashCode();
	}
	
	@Override
	public void inviteToGroup(Group group, MailGunEmailService emailer)	{
		String html = "<html>Here's the invite email message for an existing user.  Click <a href=\"https://community-shed.herokuapp.com/mygroups\">this</a> link to access your groups and accept or deny the invitation.</html>";
		try {
			emailer.sendSimpleMessage(super.getEmail(), "You've been invited to the CommunityShed group: " + group.getGroupName(), html);
		} catch (UnirestException e) {
			System.out.println("Invitation email failed");
			e.printStackTrace();
		}
	}
	
}

