package com.noone.paymentms.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Account {

	private Long parentId;

	private String accountType;

	private String loginId;
	
	@Transient
	private String inputedPassword;

	private String accountName;

	private String nonceToken;

	private String passwordSalt;

	private String passwordHash;

	private String phoneNumber;

	private String contactNumber;

	private String accountStatus;

	// -------------Auto generated start (Sun Aug 20 05:14:06 EDT 2017)-------------//

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date createdDate;

	private Long createdBy;

	private Date modifiedDate;

	private Long modifiedBy;

	public void setBaseField(Long operatorId) {
		Date now = new Date();
		if (this.getId() == null) {
			setCreatedBy(operatorId);
			setCreatedDate(now);
		}
		setModifiedBy(operatorId);
		setModifiedDate(now);
	}

	// -------------Auto generated end-------------//

}