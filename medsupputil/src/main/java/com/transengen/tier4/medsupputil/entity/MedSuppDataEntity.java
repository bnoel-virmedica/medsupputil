package com.transengen.tier4.medsupputil.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "MEDSUPPDATA")
public class MedSuppDataEntity {

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CMSRuleEntity")
	// @SequenceGenerator(name = "CMSRuleEntity", sequenceName = "", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "PLANLETTER", nullable = false)
	private String planLetter;

	@Column(name = "PATIENTCOINSURANCE", nullable = false)
	private int patientCoinsurance;

	@Column(name = "PATIENTCOPAY", nullable = false)
	private int patientCopay;

	@Column(name = "PARTB", nullable = false)
	private boolean partbDeductibleCovered;

	@Column(name = "OOPLIMIT", nullable = false)
	private double oopLimit;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlanLetter() {
		return planLetter;
	}

	public void setPlanLetter(String planLetter) {
		this.planLetter = planLetter;
	}

	public int getPatientCoinsurance() {
		return patientCoinsurance;
	}

	public void setPatientCoinsurance(int patientCoinsurance) {
		this.patientCoinsurance = patientCoinsurance;
	}

	public int getPatientCopay() {
		return patientCopay;
	}

	public void setPatientCopay(int patientCopay) {
		this.patientCopay = patientCopay;
	}

	public boolean isPartbDeductibleCovered() {
		return partbDeductibleCovered;
	}

	public void setPartbDeductibleCovered(boolean partbDeductibleCovered) {
		this.partbDeductibleCovered = partbDeductibleCovered;
	}

	public double getOopLimit() {
		return oopLimit;
	}

	public void setOopLimit(double oopLimit) {
		this.oopLimit = oopLimit;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
