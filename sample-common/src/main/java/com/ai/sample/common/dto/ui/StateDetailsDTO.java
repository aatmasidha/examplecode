package com.ai.sample.common.dto.ui;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "state")
public class StateDetailsDTO {
	String name;
	int id;
	
	public StateDetailsDTO(String name, int id) {
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
	
	
}
