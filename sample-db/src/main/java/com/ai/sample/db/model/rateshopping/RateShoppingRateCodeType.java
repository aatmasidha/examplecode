package com.ai.sample.db.model.rateshopping;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="rate_shopping_rates_codes",  uniqueConstraints=
@UniqueConstraint(columnNames={"name","rateshoppingengineid"}))
public class RateShoppingRateCodeType {
	private int id;
	private String name;
	private RateShoppingEngineData rateShoppingEngine;
	private String rateCode;
	
	public RateShoppingRateCodeType() {
		super();
	}

	public RateShoppingRateCodeType( String name,
			RateShoppingEngineData rateShoppingEngine, String rateCode) {
		super();
		this.name = name;
		this.rateShoppingEngine = rateShoppingEngine;
		this.rateCode = rateCode;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name = "rateshoppingengineid", nullable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public RateShoppingEngineData getRateShoppingEngine() {
		return rateShoppingEngine;
	}

	public void setRateShoppingEngine(RateShoppingEngineData rateShoppingEngine) {
		this.rateShoppingEngine = rateShoppingEngine;
	}

	

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	@Override
	public String toString() {
		return "RateShoppingRateCodeType [id=" + id + ", name=" + name
				+ ", rateShoppingEngine=" + rateShoppingEngine + ", updatedOn="
				+ rateCode + "]";
	}
}
