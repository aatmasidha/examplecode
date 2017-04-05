package com.ai.sample.db.model.configuration.events;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * @author aparna
 *
 */
@Entity
@Table(name="severity")
public class Severity {
	/**
	 * 
	 */
	private int id;
	
	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	public Severity() {
		super();
	}

	/**
	 * @param name
	 */
	public Severity(final String name) {
		super();
		this.name = name;
	}

	/**
	 * @return
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(final int id) {
		this.id = id;
	}
	
	
	/**
	 * @return
	 */
	@Column(name = "name", nullable = false, unique=true)
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean isEqual = false;
		if (this == obj)
		{
			isEqual = true;
		}
		if (obj == null)
		{
			isEqual =  false;
		}
		if (!(obj instanceof Severity))
		{
			isEqual =  false;
		}
		Severity other = (Severity) obj;
		if (id != other.id)
		{
			isEqual =  false;
		}
		if (name == null) {
			if (other.name != null)
			{
				isEqual =  false;
			}
		} else if (!name.equals(other.name))
		{
			isEqual =  false;
		}
		return isEqual;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name + "]";
	}
}
