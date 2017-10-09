package com.libertymutual.goforcode.communityShed.config;

import org.springframework.context.annotation.Configuration;

import com.libertymutual.goforcode.communityShed.models.Tool;
//import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
//import com.libertymutual.goforcode.communityShed.repositories.InviteRepo;
//import com.libertymutual.goforcode.communityShed.repositories.RequestRepo;
//import com.libertymutual.goforcode.communityShed.repositories.SessionRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
//import com.libertymutual.goforcode.communityShed.repositories.UserRepo;

@Configuration

public class SeedData {

	
//	public SeedData(ToolRepo toolRepo, UserRepo userRepo, GroupRepo groupRepo, InviteRepo inviteRepo, SessionRepo sessionRepo, RequestRepo requestRepo) {
	public SeedData(ToolRepo toolRepo) {
	
		toolRepo.save(new Tool("Tool1", "Description1", "Category1", "Brand1", null, null, "Available", null));
		toolRepo.save(new Tool("Tool2", "Description2", "Category2", "Brand2", null, null, "Available", null));
		toolRepo.save(new Tool("Tool3", "Description3", "Category3", "Brand3", null, null, "Available", null));
		
	}

}