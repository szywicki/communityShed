package com.libertymutual.goforcode.communityShed.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;

import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

public class ToolApiControllerTests {

	private GroupRepo groupRepo;
	private UserRepo userRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	private InvitedUserRepo invitedUserRepo;
	private GroupApiController controller;
	private Authentication auth;
	private ToolRepo toolRepo;

	@Before
	public void setUp() {
	toolRepo = mock(ToolRepo.class);
	groupRepo = mock(GroupRepo.class);
	userRepo = mock(UserRepo.class);
	confirmedUserRepo = mock(ConfirmedUserRepo.class);
	invitedUserRepo = mock(InvitedUserRepo.class);
    auth = mock(Authentication.class);
	controller = new GroupApiController(groupRepo, userRepo, confirmedUserRepo, invitedUserRepo);
	}
	
	
	@Test
	public void test_get_one_tool_by_id() throws StuffNotFoundException {

		// Arrange
		Group NKOTB = new Group();
		when(groupRepo.findOne(2l)).thenReturn(NKOTB);
		
		// Act
		Group actual = controller.getOneGroup(2l);
		
		// Assert
		assertThat(actual).isSameAs(NKOTB);
		verify(groupRepo).findOne(2l);
		
	}
	
	@Test
	public void test_createTool_saves_and_returns_tool() {

		// Arrange
		// Act
		// Assert
		
	}
	
	@Test
	public void test_Update_a_Tool_saves_and_returns_tool() {

		// Arrange
		// Act
		// Assert
		
	}
	
	@Test
	public void test_Get_all_tools_owned_by_current_user_returns_list_of_tools() {

		// Arrange
		// Act
		// Assert
		
	}
	
	@Test
	public void test_Get_allTools_of_allUsers_for_allGroups_current_user_is_in_returns_list_of_tools () {
		
		// Arrange
		// Act
		// Assert
		
	}
	
	@Test
	public void test_Enable_a_Tool_returns_enabled_Tool () {
		
		// Arrange
		// Act
		// Assert
		
	}
	
	@Test
	public void test_Disable_a_Tool_returns_disabled_Tool () {
		
		// Arrange
		// Act
		// Assert
		
	}
}


