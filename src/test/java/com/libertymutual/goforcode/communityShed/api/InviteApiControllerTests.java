package com.libertymutual.goforcode.communityShed.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.InvitedUser;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;
import com.libertymutual.goforcode.communityShed.services.MailGunEmailService;
import com.libertymutual.goforcode.communityShed.services.ShedUserDetailsService;

public class InviteApiControllerTests {
	
	private InvitedUserRepo ivr;
	private UserRepo userRepo;
	private GroupRepo groupRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	private MailGunEmailService emailer;
	private PasswordEncoder encoder;
	private ShedUserDetailsService userDetails;
	private InviteApiController controller;
	private Authentication auth;
	private SecurityContextHolder sch;
	
	@Before
	public void setUp()	{
		ivr = mock(InvitedUserRepo.class);
		userRepo = mock(UserRepo.class);
		groupRepo = mock(GroupRepo.class);
		confirmedUserRepo = mock(ConfirmedUserRepo.class);
		emailer = mock(MailGunEmailService.class);
		encoder = mock(PasswordEncoder.class);
		userDetails = mock(ShedUserDetailsService.class);
		auth = mock(Authentication.class);
		sch = mock(SecurityContextHolder.class);
		controller = new InviteApiController(userRepo, groupRepo, confirmedUserRepo, ivr, emailer, encoder, userDetails);
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
		List<Group> pendingGroups = new ArrayList<Group>();
		pendingGroups.add(group1);
		invited.setPendingGroups(pendingGroups);
		
		when(ivr.findByInvitationKey(key)).thenReturn(invited);
		when(confirmedUserRepo.save(confirmed)).thenReturn(confirmed);
		when(encoder.encode(confirmed.getPassword())).thenReturn("12345");
		when(userDetails.loadUserByUsername("a@a.com")).thenReturn(confirmed);
		

		
		//Act
		ConfirmedUser confirmedUserReturned = controller.convertInvitedUserAndLogin(confirmed, key);
		
		//Assert
		verify(groupRepo, times(2)).save(group1);
		verify(ivr).delete(invited);
		verify(confirmedUserRepo).save(confirmed);
		verify(userDetails).loadUserByUsername("a@a.com");
		assertThat(group1.getUsers()).contains(confirmed);
//		assertThat()
		assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isSameAs(confirmed);
		
		
	}

}
