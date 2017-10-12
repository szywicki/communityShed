package com.libertymutual.goforcode.communityShed.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
	private Tool tool;
	
	@ManyToOne
	private ConfirmedUser borrower;
	
	@ManyToOne
	private ConfirmedUser loaner;
	
	public Request() {}
	
	public Request(Date loanStartDate, Date loanEndDate, String description, String status, Tool tool, ConfirmedUser borrower, ConfirmedUser loaner) {
		this.loanStartDate = loanStartDate;
		this.loanEndDate = loanEndDate;
		this.description = description;
		this.status = status;
		this.tool = tool;
		this.borrower = borrower;
		this.loaner = loaner;
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

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	public User getBorrower() {
		return borrower;
	}

	public void setBorrower(ConfirmedUser borrower) {
		this.borrower = borrower;
	}

	public User getLoaner() {
		return loaner;
	}

	public void setLoaner(ConfirmedUser loaner) {
		this.loaner = loaner;
	}
	
	public String getLoanStartDateForBrowser()	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(loanStartDate);
	}
}
