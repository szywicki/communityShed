package com.libertymutual.goforcode.communityShed.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Request {
	
	@Id
	@GeneratedValue(generator = "RequestIdSeq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "RequestIdSeq", sequenceName = "RequestIdSeq")
	private Long id;
	
	@Column(nullable=false)
	private Date loanStartDate;
	
	@Column(nullable=false)
	private Date loanEndDate;
	
	@Column
	private String description;
	
	@Column(nullable=false)
	private String status;
	
	public Request() {}
	
	public Request(Date loanStartDate, Date loanEndDate, String description, String status) {
		this.loanStartDate = loanStartDate;
		this.loanEndDate = loanEndDate;
		this.description = description;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
