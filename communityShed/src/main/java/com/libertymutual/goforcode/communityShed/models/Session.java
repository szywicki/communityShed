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

public class Session {
	@Id
	@GeneratedValue(generator = "SessionIdSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "SessionIdSeq", sequenceName = "SessionIdSeq")
	private Long id;
}
