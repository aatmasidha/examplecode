package com.ai.sample.common.dto;

import java.util.Date;

public class CompetitorDetailsDTO {
	
//	This is the property details for which hotel selected property is
//	marked as competitor.
	PropertyDetailsDTO propertyDetailsDto;
	PropertyDetailsDTO competitorPropertyDetailsDto;
	int competitorSequence;
	boolean isActive;
	Date updateDate;
	
	public CompetitorDetailsDTO(PropertyDetailsDTO propertyDetailsDto,
			PropertyDetailsDTO competitorPropertyDetailsDto,
			int competitorSequence, boolean isActive, Date updateDate) {
		super();
		this.propertyDetailsDto = propertyDetailsDto;
		this.competitorPropertyDetailsDto = competitorPropertyDetailsDto;
		this.competitorSequence = competitorSequence;
		this.isActive = isActive;
		this.updateDate = updateDate;
	}

	public CompetitorDetailsDTO() {
		super();
	}

	public PropertyDetailsDTO getPropertyDetailsDto() {
		return propertyDetailsDto;
	}

	public void setPropertyDetailsDto(PropertyDetailsDTO propertyDetailsDto) {
		this.propertyDetailsDto = propertyDetailsDto;
	}

	public PropertyDetailsDTO getCompetitorPropertyDetailsDto() {
		return competitorPropertyDetailsDto;
	}

	public void setCompetitorPropertyDetailsDto(
			PropertyDetailsDTO competitorPropertyDetailsDto) {
		this.competitorPropertyDetailsDto = competitorPropertyDetailsDto;
	}

	public int getCompetitorSequence() {
		return competitorSequence;
	}

	public void setCompetitorSequence(int competitorSequence) {
		this.competitorSequence = competitorSequence;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "CompetitorDetailsDTO [propertyDetailsDto=" + propertyDetailsDto
				+ ", competitorPropertyDetailsDto="
				+ competitorPropertyDetailsDto + ", competitorSequence="
				+ competitorSequence + ", isActive=" + isActive
				+ ", updateDate=" + updateDate + "]";
	}

}
