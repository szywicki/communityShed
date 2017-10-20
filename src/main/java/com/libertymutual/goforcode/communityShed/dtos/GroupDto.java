package com.libertymutual.goforcode.communityShed.dtos;

import com.libertymutual.goforcode.communityShed.models.Group;

public class GroupDto {
	
	private Group group;

	public GroupDto(Group group) {
		this.group = group;
	}
	
	public Long getId() {
		return group.getId();
	}
	
	public String getGroupName() {
		return group.getGroupName();
	}

}
