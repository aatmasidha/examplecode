package com.ai.sample.common.dto;

public class CurrencyDTO {
	private int id;
	private String code;
	private String description;
	

	public CurrencyDTO() {
		super();
	}

	public CurrencyDTO(int id, String code, String description) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
	}

	
	public CurrencyDTO(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CurrencyDTO))
			return false;
		CurrencyDTO other = (CurrencyDTO) obj;
		if (id != other.id)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CurrencyDTO [id=" + id + ", code=" + code + ", description="
				+ description + "]";
	}
}
