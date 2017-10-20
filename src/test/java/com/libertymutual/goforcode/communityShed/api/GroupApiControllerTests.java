//package com.libertymutual.goforcode.communityShed.api;
//
//import static org.junit.Assert.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//
//import com.libertymutual.goforcode.communityShed.dtos.PendingGroupDto;
//import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
//import com.libertymutual.goforcode.communityShed.models.Group;
//import com.libertymutual.goforcode.communityShed.models.Tool;
//import com.libertymutual.goforcode.communityShed.models.User;
//import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
//import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
//import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;
//import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
//import com.libertymutual.goforcode.communityShed.repositories.UserRepo;
//
//public class GroupApiControllerTests {
//	
//	private GroupRepo groupRepo;
//	private UserRepo userRepo;
//	private ConfirmedUserRepo confirmedUserRepo;
//	private InvitedUserRepo invitedUserRepo;
//	private GroupApiController controller;
//	private Authentication auth;
//	private ToolRepo toolRepo;
//
//	@Before
//	public void setUp() {
//		groupRepo = mock(GroupRepo.class);
//		userRepo = mock(UserRepo.class);
//		confirmedUserRepo = mock(ConfirmedUserRepo.class);
//		invitedUserRepo = mock(InvitedUserRepo.class);
//        auth = mock(Authentication.class);
//		controller = new GroupApiController(groupRepo, userRepo, confirmedUserRepo, invitedUserRepo);
//	}
//	
//	@Test
//	public void test_get_one_group_by_id() throws StuffNotFoundException {
//		// Arrange
//		Group NKOTB = new Group();
//		when(groupRepo.findOne(2l)).thenReturn(NKOTB);
//		
//		// Act
//		Group actual = controller.getOneGroup(2l);
//		
//		// Assert
//		assertThat(actual).isSameAs(NKOTB);
//		verify(groupRepo).findOne(2l);
//		
//	}
//	
//	@Test
//	public void test_createGroup_saves_and_returns_group() {
//		// Arrange
//		Group NKOTB = new Group();
//		ConfirmedUser currentUser = new ConfirmedUser();
//		when(groupRepo.save(NKOTB)).thenReturn(NKOTB);
//		when(auth.getPrincipal()).thenReturn((User)currentUser);
//		
//		// Act
//		Group actual = controller.createGroup(NKOTB, auth);
//		
//		// Assert 
//		verify(groupRepo).save(NKOTB);
//		assertThat(NKOTB.getUsers()).contains(currentUser);
//		assertThat(NKOTB).isSameAs(actual);
//	}
//	
//	@Test
//	public void test_getUsers_returns_list_of_users() {
//		// Arrange
//		HashSet<User> users = new HashSet<User>();
//		Group NKOTB = new Group();
//		when(groupRepo.findOne(3l)).thenReturn(NKOTB);
//		
//		// Act
//		Set<User> actual = controller.getUsers(3l);
//		
//		// Assert
//		verify(groupRepo).findOne(3l);
//		assertThat(NKOTB.getUsers()).isSameAs(actual);
//	}
//
//	@Test
//	public void test_getPendingGroups_returns_list_of_pendingGroups() {
//		// Arrange
//		Group group = new Group();
//		group.setId(4L);
//		group.setGroupName("Boy, howdy!");
//		HashSet<Group> groups = new HashSet<Group>();
//		groups.add(group);
//		ConfirmedUser currentUser = new ConfirmedUser();
//		currentUser.setId(1l);
//		currentUser.setPendingGroups(groups);
//		
//		when(auth.getPrincipal()).thenReturn((User)currentUser);
//		when(confirmedUserRepo.findOne(1l)).thenReturn(currentUser);
//		
//		// Act
//		List<PendingGroupDto> actual = controller.getPendingGroups(auth);
//		
//		// Assert
//		verify(auth).getPrincipal();
//		verify(confirmedUserRepo).findOne(1l);
//		assertThat(actual.size()).isEqualTo(1);
//		PendingGroupDto dto = actual.get(0);
//		assertThat(dto.getId()).isEqualTo(4L);
//		assertThat(dto.getGroupName()).isEqualTo("Boy, howdy!");
//	}
//	
//	@Test
//	public void test_acceptInvite_returns_list_of_user_groups_that_includes_new_group() {
//		// Arrange
//		HashSet<Group> groups = new HashSet<Group>();
//		HashSet<Group> pendingGroups = new HashSet<Group>();
//		ConfirmedUser currentUser = new ConfirmedUser();
//		Group NKOTB = new Group();
//		NKOTB.addPendingUserToGroup(currentUser);
//		pendingGroups.add(NKOTB);
//		currentUser.setGroups(groups);
//		currentUser.setPendingGroups(pendingGroups);
//		when(groupRepo.save(NKOTB)).thenReturn(NKOTB);
//		when(groupRepo.findOne(3l)).thenReturn(NKOTB);
//		when(auth.getPrincipal()).thenReturn((User)currentUser);
//		
//		// Act
//		Set<Group> actual = controller.acceptInvite(3l, auth);
//		
//		// Assert
//		verify(auth).getPrincipal();
//		verify(groupRepo).save(NKOTB);
//		assertThat(actual).contains(NKOTB);
//		assertThat(NKOTB.getPendingUsers()).doesNotContain(currentUser);
//	}
//	
//	@Test
//	public void test_denyInvite_returns_list_of_user_groups_not_including_pending_group() {
//				// Arrange
//				HashSet<Group> groups = new HashSet<Group>();
//				HashSet<Group> pendingGroups = new HashSet<Group>();
//				ConfirmedUser currentUser = new ConfirmedUser();
//				Group NKOTB = new Group();
//				NKOTB.addPendingUserToGroup(currentUser);
//				pendingGroups.add(NKOTB);
//				currentUser.setId(1l);
//				currentUser.setPendingGroups(pendingGroups);
//				when(groupRepo.save(NKOTB)).thenReturn(NKOTB);
//				when(groupRepo.findOne(3l)).thenReturn(NKOTB);
//				when(confirmedUserRepo.findOne(1l)).thenReturn(currentUser);
//				when(auth.getPrincipal()).thenReturn((User)currentUser);
//				
//				// Act
//				Set<Group> actual = controller.denyInvite(3l, auth);
//				
//				// Assert
//				verify(auth).getPrincipal();
//				verify(groupRepo).save(NKOTB);
//				assertThat(actual).isNull();
////				assertThat(NKOTB.getPendingUsers()).doesNotContain(currentUser);
//	}
	
//	@Test
//	public void test_getTools() {
//		// Arrange
//		ArrayList<User> users = new ArrayList<User>();
//		ArrayList<Tool> tools = new ArrayList<Tool>();
//		ConfirmedUser currentUser = new ConfirmedUser();
//		Tool tool = new Tool();
//		Group group = new Group();
//		group.setId(3l);
//		when(groupRepo.findOne(3l)).thenReturn(group);
////		when(toolRepo.findAll(group.getUsers()).thenReturn(user.getTools());
//		
//		// Act
//		List<Tool> actual = controller.getTools(3l);
//		
//		// Assert
//		verify(auth).getPrincipal();
//		verify(groupRepo).save(group);
//		verify(groupRepo).findOne(3l);
//		assertThat(actual).isNull();
//		assertThat(group.getPendingUsers()).doesNotContain(currentUser);
//		
//	}
	
//	@Test
//	public void test_getGroups_returns_list_of_groups_user_is_member_of() {
//				// Arrange
//				ArrayList<Group> groups = new ArrayList<Group>();
//				ConfirmedUser currentUser = new ConfirmedUser();
//				Group NKOTB = new Group();
//				currentUser.setId(1l);
//				currentUser.addGroup(NKOTB);
//				NKOTB.addUserToGroup(currentUser);
//				when(confirmedUserRepo.findOne(1l)).thenReturn(currentUser);
//				when(auth.getPrincipal()).thenReturn((User)currentUser);
//				
//				
//				// Act
//				List<Group> actual = controller.getGroups(auth);
//				
//				// Assert
//				verify(groupRepo).findOne(2l);
//				verify(auth).getPrincipal();
//				assertThat(actual).contains(NKOTB);
//		
//	}
//	
////	@Test
////	public void test_denyInvite_returns_list_of_user_groups_not_including_pending_group() {
////				// Arrange
//////				ArrayList<Group> groups = new ArrayList<Group>();
////				ArrayList<Group> pendingGroups = new ArrayList<Group>();
////				ConfirmedUser currentUser = new ConfirmedUser();
////				Group NKOTB = new Group();
////				pendingGroups.add(NKOTB);
////				NKOTB.addPendingUserToGroup(currentUser);
////				currentUser.setPendingGroups(pendingGroups);
////				when(groupRepo.findOne(3l)).thenReturn(NKOTB);
////				when(groupRepo.save(NKOTB)).thenReturn(NKOTB);
////				when(auth.getPrincipal()).thenReturn((User)currentUser);
////				
////				// Act
////				List<Group> actual = controller.denyInvite(3l, auth);
////				
////				// Assert
////				verify(auth).getPrincipal();
////				verify(groupRepo).save(NKOTB);
////				assertThat(actual).doesNotContain(NKOTB);
////				assertThat(NKOTB.getPendingUsers()).doesNotContain(currentUser);
//////	}
//}
