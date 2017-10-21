//package com.libertymutual.goforcode.communityShed.api;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
//import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;
//import com.libertymutual.goforcode.communityShed.services.ShedUserDetailsService;
//
//public class SessionApiControllerTests {
//
//	private ShedUserDetailsService userDetails;
//	private AuthenticationManager authManager;
//	private ConfirmedUserRepo confirmedUserRepo;
//	private SessionApiController controller;
//	private Authentication auth;
//
//
//	@Before
//	public void setUp() { 
//		
//		controller = new SessionApiController(userDetails, authManager, confirmedUserRepo);
//		confirmedUserRepo = mock(ConfirmedUserRepo.class);
//		userDetails = mock(ShedUserDetailsService.class);
//		auth = mock(Authentication.class);
//	}
//
//	@Test
//	public void test_that_login_runs_and_returns_credentials() {
//
//		
//		// Arrange
//		when(userDetails.loadUserByUsername("bob@ross.com")).thenReturn(userDetails);
//	
//		// Act
//		User userlogged = controller.login(credentials.);
//		ConfirmedUser confirmedUserReturned = controller.login(credentials);
//		
//		// Assert
//		verify(userDetails).loadUserByUsername("bob@ross.com");
//		assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isSameAs(userDetails);
//	}
//	
//	@Test
//	public void test_that_logout_runs_and_destroys_user_credentials() {
//		fail("Not yet implemented");
//		
//		// Arrange
//		when(userDetails.loadUserByUsername("bob@ross.com")).thenReturn(userDetails);
//		
//		// Act
//		User userlogged = controller.logout(credentials.);
//		
//		// Assert
//		assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isSameAs(confirmed);
//	}
//
//}

