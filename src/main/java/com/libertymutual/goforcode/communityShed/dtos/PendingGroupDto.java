package com.libertymutual.goforcode.communityShed.dtos;

import com.libertymutual.goforcode.communityShed.models.Group;

public class PendingGroupDto {
	
	private Group group;

	public PendingGroupDto(Group group) {
		this.group = group;
	}
	
	public Long getId() {
		return group.getId();
	}
	
	public String getGroupName() {
		return group.getGroupName();
	}

}
