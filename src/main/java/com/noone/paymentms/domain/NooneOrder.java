package com.noone.paymentms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import java.util.Date;

@Data
@Entity
public class NooneOrder {

	private String orderNum;
	private String payCode;
	private Double totalFee;

	// -------------Auto generated start (Mon Dec 11 07:35:55 CST 2017)-------------//

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