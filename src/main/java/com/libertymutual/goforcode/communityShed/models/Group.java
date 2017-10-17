package com.libertymutual.goforcode.communityShed.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "communityshed_group")

public class Group {
	@Id
	@GeneratedValue(generator = "GroupIdSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "GroupIdSeq", sequenceName = "GroupIdSeq")
	private Long id;

	@Column(nullable = false, length = 30)
	private String groupName;

	@Column(nullable = false, length = 80)
	private String groupDescription;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> users;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> pendingUsers;

	public Group() {
	}

	public Group(String groupName, String groupDescription) {
		this.groupName = groupName;
		this.groupDescription = groupDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addUserToGroup(User user) {
		if (users == null) {
			users = new ArrayList<User>();
		}
		users.add(user);
	}

	public void removeUserFromGroup(User user) {
		users.remove(user);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addPendingUserToGroup(User user) {
		if (pendingUsers == null) {
			pendingUsers = new ArrayList<User>();
		}
		pendingUsers.add(user);
	}

	public void removePendingUserFromGroup(User user) {
		if (pendingUsers != null) {
			pendingUsers.remove(user);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Group) {
			Group g = (Group) o;
			return g.getId() == getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (getId() == null) {
			return 0;
		}
		return getId().hashCode();
	}
}
