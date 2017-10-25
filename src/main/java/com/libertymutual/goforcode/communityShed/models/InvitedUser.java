package com.libertymutual.goforcode.communityShed.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.libertymutual.goforcode.communityShed.services.MailGunEmailService;
import com.mashape.unirest.http.exceptions.UnirestException;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class, 
		property = "id"
)
@Entity
public class InvitedUser extends User{

	private static final long serialVersionUID = 1L;

	@Column(unique=true)
	private UUID invitationKey = UUID.randomUUID();
	
	public InvitedUser() {}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>();
	}

	@Override
	public String getPassword() {
		return null;
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
	public List<Tool> getTools() {
		return new ArrayList<Tool>();
	}

	public UUID getInvitationKey() {
		return invitationKey;
	}

	public void setInvitationKey(UUID invitationKey) {
		this.invitationKey = invitationKey;
	}
	
	@Override
	public void inviteToGroup(Group group, MailGunEmailService emailer) {
		String html = 	"<html><body>"
						+ "<div><h3>Your friend has invited you to join the Community Shed group " + group.getGroupName()+ ".</h3>"
						+ "<br>Click <a href=\"https://community-shed.herokuapp.com/invite/" + invitationKey + "\">this</a> link to create an account and start sharing!</div>"
						+ "</body></html>";
		try {
			emailer.sendSimpleMessage(super.getEmail(), "You've been invited to the Community Shed group: " + group.getGroupName(), html);
		} catch (UnirestException e) {
			System.out.println("Invitation email failed");
			e.printStackTrace();
		}
		
	}
	
}
