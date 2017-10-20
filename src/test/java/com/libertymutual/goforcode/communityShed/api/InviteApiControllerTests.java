package com.libertymutual.goforcode.communityShed.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.InvitedUser;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;
import com.libertymutual.goforcode.communityShed.services.ConfirmedUserService;
import com.libertymutual.goforcode.communityShed.services.InvitationService;
import com.libertymutual.goforcode.communityShed.services.ShedUserDetailsService;

public class InviteApiControllerTests {
	
	private InvitedUserRepo ivr;
	private UserRepo userRepo;
	private GroupRepo groupRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	private PasswordEncoder encoder;
	private ShedUserDetailsService userDetails;
	private InviteApiController controller;
	private Authentication auth;
	private ConfirmedUserService cus;
	private InvitationService inviteService;
	private HttpServletResponse httpResponse;
	
	@Before
	public void setUp()	{
		ivr = mock(InvitedUserRepo.class);
		userRepo = mock(UserRepo.class);
		groupRepo = mock(GroupRepo.class);
		confirmedUserRepo = mock(ConfirmedUserRepo.class);
		encoder = mock(PasswordEncoder.class);
		userDetails = mock(ShedUserDetailsService.class);
		auth = mock(Authentication.class);
		cus = mock(ConfirmedUserService.class);
		inviteService = mock(InvitationService.class);
		httpResponse = mock(HttpServletResponse.class);
		controller = new InviteApiController(userRepo, groupRepo, confirmedUserRepo, ivr, encoder, userDetails, cus, inviteService);
	}

	@Test
	public void test_that_GetUserFromInvite_returns_the_user_associated_with_the_invite() {
		//Arrange
		InvitedUser invited = new InvitedUser();
		UUID key = UUID.randomUUID();
		invited.setInvitationKey(key);
		when(ivr.findByInvitationKey(key)).thenReturn(invited);
		
		//Act
		InvitedUser invitedReturned = controller.getUserFromInvite(key);
		
		//Assert
		assertThat(invitedReturned).isSameAs(invited);
		verify(ivr).findByInvitationKey(key);
	}
	
	@Test
	public void test_convertInvitedUserAndLogin_creates_confirmedUser_from_invitedUser_and_logs_them_in()	{
		//Arrange
		ConfirmedUser confirmed = new ConfirmedUser("test", "a@a.com", "First", "Last");
		InvitedUser invited = new InvitedUser();
		invited.setEmail("a@a.com");
		UUID key = UUID.randomUUID();
		invited.setInvitationKey(key);
		Group group1 = new Group();
		group1.addPendingUserToGroup(invited);
		Set<Group> pendingGroups = new HashSet<Group>();
		pendingGroups.add(group1);
		invited.setPendingGroups(pendingGroups);
		
		when(ivr.findByInvitationKey(key)).thenReturn(invited);
		when(cus.convertInvitedUserToConfirmedUser(confirmed, invited)).thenReturn(confirmed);
		when(userDetails.loadUserByUsername("a@a.com")).thenReturn(confirmed);
		

		
		//Act
		ConfirmedUser confirmedUserReturned = controller.convertInvitedUserAndLogin(confirmed, key, httpResponse);
		
		//Assert
		verify(ivr).findByInvitationKey(key);
		verify(userDetails).loadUserByUsername("a@a.com");
		assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isSameAs(confirmed);
		assertThat(confirmedUserReturned).isSameAs(confirmed);
	}
	
	@Test
	public void test_convertInvitedUserAndLogin_returns_null_if_confirmedUserService_returns_a_null()	{
		//Arrange
		ConfirmedUser confirmed = new ConfirmedUser("test", "a@a.com", "First", "Last");
		InvitedUser invited = new InvitedUser();
		invited.setEmail("a@b.com");
		UUID key = UUID.randomUUID();
		invited.setInvitationKey(key);
		
		when(ivr.findByInvitationKey(key)).thenReturn(invited);
		when(cus.convertInvitedUserToConfirmedUser(confirmed, invited)).thenReturn(null);
		
		//Act
		ConfirmedUser confirmedUserReturned = controller.convertInvitedUserAndLogin(confirmed, key, httpResponse);
		
		//Assert
		verify(ivr).findByInvitationKey(key);
		assertThat(confirmedUserReturned).isNull();
	}
	
	@Test
	public void test_inviteUser_returns_true_when_sendInvitation_returns_true()	{
		//Arrange
		ConfirmedUser authUser = new ConfirmedUser("test", "a@a.com", "First", "Last");
		authUser.setId(1L);
		User existingUser = new ConfirmedUser("test2", "newguy@domain.com", "First", "Last");
		InviteApiController.inviteEmail input = new InviteApiController.inviteEmail();
		input.setEmail("newguy@domain.com");
		Group group = new Group();
		
		when(userRepo.findByEmail("newguy@domain.com")).thenReturn(existingUser);
		when(groupRepo.findOne(1L)).thenReturn(group);
		when(auth.getPrincipal()).thenReturn(authUser);
		when(confirmedUserRepo.findOne(1L)).thenReturn(authUser);
		when(inviteService.sendInvitation(authUser, existingUser, group, input.getEmail())).thenReturn(true);
		
		//Act
		Boolean invitationSent = controller.inviteUser(auth, input, 1L, httpResponse);
		
		//Assert
		verify(userRepo).findByEmail("newguy@domain.com");
		verify(groupRepo).findOne(1L);
		verify(auth).getPrincipal();
		verify(confirmedUserRepo).findOne(1L);
		verify(inviteService).sendInvitation(authUser, existingUser, group, input.getEmail());
		assertThat(invitationSent).isTrue();
		
	}
	
	@Test
	public void test_inviteUser_returns_false_when_sendInvitation_returns_false()	{
		//Arrange
		ConfirmedUser authUser = new ConfirmedUser("test", "a@a.com", "First", "Last");
		authUser.setId(1L);
		User existingUser = new ConfirmedUser("test2", "newguy@domain.com", "First", "Last");
		InviteApiController.inviteEmail input = new InviteApiController.inviteEmail();
		input.setEmail("newguy@domain.com");
		Group group = new Group();
		
		when(userRepo.findByEmail("newguy@domain.com")).thenReturn(existingUser);
		when(groupRepo.findOne(1L)).thenReturn(group);
		when(auth.getPrincipal()).thenReturn(authUser);
		when(confirmedUserRepo.findOne(1L)).thenReturn(authUser);
		when(inviteService.sendInvitation(authUser, existingUser, group, input.getEmail())).thenReturn(false);
		
		//Act
		Boolean invitationSent = controller.inviteUser(auth, input, 1L, httpResponse);
		
		//Assert
		verify(userRepo).findByEmail("newguy@domain.com");
		verify(groupRepo).findOne(1L);
		verify(auth).getPrincipal();
		verify(confirmedUserRepo).findOne(1L);
		verify(inviteService).sendInvitation(authUser, existingUser, group, input.getEmail());
		assertThat(invitationSent).isFalse();
		
	}

}
