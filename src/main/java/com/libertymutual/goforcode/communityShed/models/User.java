package com.libertymutual.goforcode.communityShed.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity

public class User {
	@Id
	@GeneratedValue(generator = "UserIdSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "UserIdSeq", sequenceName = "UserIdSeq")
	private Long id;
}
