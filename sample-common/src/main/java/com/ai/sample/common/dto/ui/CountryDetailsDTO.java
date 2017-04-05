package com.ai.sample.common.dto.ui;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "country")
public class CountryDetailsDTO {

	String name;
	int id;
	List<StateDetailsDTO> stateDetailsDtoList =  new ArrayList<StateDetailsDTO>();

	public CountryDetailsDTO(){
		
	}
	
	public CountryDetailsDTO(int id, String name, List<StateDetailsDTO> stateDetailsDTOList) {
		this.id = id;
		this.name = name;
		this.stateDetailsDtoList = stateDetailsDTOList;
	}

	public CountryDetailsDTO(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}
	
	@XmlElement
	public int getId() {
		return id;
	}

	@XmlElement
	public List<StateDetailsDTO> getStateDetailsDtoList() {
		return stateDetailsDtoList;
	}
	
	
}
