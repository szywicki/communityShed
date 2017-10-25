package com.libertymutual.goforcode.communityShed.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.core.Authentication;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Request;
import com.libertymutual.goforcode.communityShed.models.SimpleTool;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.InvitedUserRepo;
import com.libertymutual.goforcode.communityShed.repositories.RequestRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

public class ToolApiControllerTests {

	private RequestRepo requestRepo;
	private ConfirmedUserRepo confirmedUserRepo;
	private ToolApiController controller;
	private Authentication auth;
	private ToolRepo toolRepo;
	private SimpleTool simpleTool;

	@Before
	public void setUp() {
		toolRepo = mock(ToolRepo.class);
		mock(GroupRepo.class);
		mock(UserRepo.class);
		confirmedUserRepo = mock(ConfirmedUserRepo.class);
		mock(InvitedUserRepo.class);
		auth = mock(Authentication.class);
		controller = new ToolApiController(toolRepo, requestRepo, confirmedUserRepo);
	}

	@Test
	public void test_get_one_tool_by_id() throws StuffNotFoundException {

		// Arrange
		Tool toool = new Tool();
		when(toolRepo.findOne(2l)).thenReturn(toool);

		// Act
		Tool actual = controller.getOneTool(2l);

		// Assert
		assertThat(actual).isSameAs(toool);
		verify(toolRepo).findOne(2l);

	}


	@Test
	public void test_Update_a_Tool_saves_and_returns_tool() {

		// Arrange
		Tool tool = new Tool();
		SimpleTool simpleTool = new SimpleTool();
		when(toolRepo.save(tool)).thenReturn(tool);
		when(toolRepo.findOne(3l)).thenReturn(tool);

		tool.copyFromSimpleTool(simpleTool);

		// Act
		Tool actual = controller.updateTool(simpleTool, 3l);

		// Assert
		verify(toolRepo).findOne(3l);
		assertThat(tool).isSameAs(actual);

	}

	@Test
	public void test_Get_all_tools_owned_by_current_user_returns_list_of_tools() {

		// Arrange

		// ConfirmedUser confirmedUser = new ConfirmedUser();
		// when(userRepo.findOne(3l)).thenReturn();

		// Act
		// User actual = controller.getTools();

		// Assert

	}

	@Test
	public void test_Get_allTools_of_allUsers_for_allGroups_current_user_is_in_returns_list_of_tools() {

		// Arrange
		// Act
		// Assert

	}

	@Test
	public void test_Enable_a_Tool_returns_enabled_Tool() {

		// Arrange
		Tool tool = new Tool();
		SimpleTool simpleTool = new SimpleTool();
		when(toolRepo.findOne(3L)).thenReturn(tool);
		tool.copyFromSimpleTool(simpleTool);

		// Act
		Tool actual = controller.enableTool(3l);

		// Assert
		verify(toolRepo).findOne(3L);
		assertThat(tool).isSameAs(actual);

	}
}
