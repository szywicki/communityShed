package com.libertymutual.goforcode.communityShed.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.InvitedUser;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;

public class ConfirmedUserServiceTests {

	private InvitedUserRepo ivr;
	private GroupRepo groupRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	private ConfirmedUserService cus;

	@Before
	public void setUp() {
		ivr = mock(InvitedUserRepo.class);
		groupRepo = mock(GroupRepo.class);
		confirmedUserRepo = mock(ConfirmedUserRepo.class);
		cus = new ConfirmedUserService(groupRepo, confirmedUserRepo, ivr);
	}

	@Test
	public void test_convertInvitedUserToConfirmedUser_creates_confirmedUser_from_invitedUser_and_cleans_up_groups() {
		// Arrange
		ConfirmedUser user = new ConfirmedUser("test", "a@a.com", "First", "Last");
		InvitedUser invited = new InvitedUser();
		invited.setEmail("a@a.com");
		Group group1 = new Group();
		group1.addPendingUserToGroup(invited);
		Set<Group> pendingGroups = new HashSet<Group>();
		pendingGroups.add(group1);
		invited.setPendingGroups(pendingGroups);

		when(confirmedUserRepo.save(user)).thenReturn(user);

		// Act
		ConfirmedUser confirmedUserReturned = cus.convertInvitedUserToConfirmedUser(user, invited);

		// Assert
		verify(groupRepo, times(2)).save(group1);
		verify(ivr).delete(invited);
		verify(confirmedUserRepo).save(user);
		assertThat(group1.getUsers()).contains(user);
		assertThat(group1.getPendingUsers()).doesNotContain(user);
		assertThat(confirmedUserReturned).isSameAs(user);

	}
	
	@Test
	public void test_convertInvitedUserToConfirmedUser_returns_null_when_emails_do_not_match()	{
		//Arrange
		ConfirmedUser user = new ConfirmedUser("test", "a@a.com", "First", "Last");
		InvitedUser invited = new InvitedUser();
		invited.setEmail("a@b.com");
		
		//Act
		ConfirmedUser confirmedUserReturned = cus.convertInvitedUserToConfirmedUser(user, invited);
		
		//Assert
		assertThat(confirmedUserReturned).isNull();
	}

}
