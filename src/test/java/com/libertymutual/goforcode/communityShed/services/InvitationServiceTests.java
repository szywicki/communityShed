package com.libertymutual.goforcode.communityShed.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;

public class InvitationServiceTests {
	
	private GroupRepo groupRepo;
	private InvitedUserRepo invitedUserRepo;
	private MailGunEmailService emailer;
	private InvitationService inviteService;
	
	@Before
	public void setUp()	{
		this.groupRepo = mock(GroupRepo.class);
		this.invitedUserRepo = mock(InvitedUserRepo.class);
		this.emailer = mock(MailGunEmailService.class);
		inviteService = new InvitationService(groupRepo, invitedUserRepo, emailer);
	}

	@Test
	public void test_sendInvitation_returns_true_and_creates_invited_user_when_sending_user_is_in_group_and_existingUser_is_null() {
		//Arrange
		Group group = new Group();
		ConfirmedUser authUser = new ConfirmedUser("test", "a@a.com", "First", "Last");
		Set<Group> groups = new HashSet<Group>();
		groups.add(group);
		authUser.setGroups(groups);
		User existingUser = null;
		
		//Act
		Boolean invitationSent = inviteService.sendInvitation(authUser, existingUser, group, "new@user.com");
		
		//Assert
		verify(groupRepo).save(group);
		assertThat(group.getPendingUsers()).hasSize(1);
		assertThat(invitationSent).isEqualTo(true);
		
	}
	
	@Test
	public void test_sendInvitation_returns_true_and_updates_group_with_pending_existingUser_when_sending_user_is_in_group_and_existingUser_is_not_null_and_not_within_group_already() {
		//Arrange
		Group group = new Group();
		ConfirmedUser authUser = new ConfirmedUser("test", "a@a.com", "First", "Last");
		Set<Group> groups = new HashSet<Group>();
		groups.add(group);
		authUser.setGroups(groups);
		Set<Group> existingGroups = new HashSet<Group>();
		Set<Group> existingPendingGroups = new HashSet<Group>();
		User existingUser = new ConfirmedUser("test2", "existing@user.com", "Another", "Name");
		existingUser.setGroups(existingGroups);
		existingUser.setPendingGroups(existingPendingGroups);
		
		//Act
		Boolean invitationSent = inviteService.sendInvitation(authUser, existingUser, group, "existing@user.com");
		
		//Assert
		verify(groupRepo).save(group);
		assertThat(group.getPendingUsers()).contains(existingUser);
		assertThat(invitationSent).isEqualTo(true);		
	}
	
	@Test
	public void test_sendInvitation_returns_false_when_sending_user_is_not_in_group() {
		//Arrange
		Group group = new Group();
		ConfirmedUser authUser = new ConfirmedUser("test", "a@a.com", "First", "Last");
		Set<Group> groups = new HashSet<Group>();
		authUser.setGroups(groups);
		User existingUser = null;
		
		//Act
		Boolean invitationSent = inviteService.sendInvitation(authUser, existingUser, group, "existing@user.com");
		
		//Assert
		assertThat(invitationSent).isEqualTo(false);	
	}
	
	@Test
	public void test_sendInvitation_returns_false_when_sending_user_is_in_group_and_existingUser_is_not_null_but_is_within_group_already() {
		//Arrange
		Group group = new Group();
		ConfirmedUser authUser = new ConfirmedUser("test", "a@a.com", "First", "Last");
		Set<Group> groups = new HashSet<Group>();
		groups.add(group);
		authUser.setGroups(groups);
		Set<Group> existingGroups = new HashSet<Group>();
		existingGroups.add(group);
		Set<Group> existingPendingGroups = new HashSet<Group>();
		User existingUser = new ConfirmedUser("test2", "existing@user.com", "Another", "Name");
		existingUser.setGroups(existingGroups);
		existingUser.setPendingGroups(existingPendingGroups);
		
		//Act
		Boolean invitationSent = inviteService.sendInvitation(authUser, existingUser, group, "existing@user.com");
		
		//Assert
		assertThat(invitationSent).isEqualTo(false);		
	}
	
	@Test
	public void test_sendInvitation_returns_false_when_sending_user_is_in_group_and_existingUser_is_not_null_but_is_within_pending_group_already() {
		//Arrange
		Group group = new Group();
		ConfirmedUser authUser = new ConfirmedUser("test", "a@a.com", "First", "Last");
		Set<Group> groups = new HashSet<Group>();
		groups.add(group);
		authUser.setGroups(groups);
		Set<Group> existingGroups = new HashSet<Group>();
		Set<Group> existingPendingGroups = new HashSet<Group>();
		existingPendingGroups.add(group);
		User existingUser = new ConfirmedUser("test2", "existing@user.com", "Another", "Name");
		existingUser.setGroups(existingGroups);
		existingUser.setPendingGroups(existingPendingGroups);
		
		//Act
		Boolean invitationSent = inviteService.sendInvitation(authUser, existingUser, group, "existing@user.com");
		
		//Assert
		assertThat(invitationSent).isEqualTo(false);		
	}
	

}
