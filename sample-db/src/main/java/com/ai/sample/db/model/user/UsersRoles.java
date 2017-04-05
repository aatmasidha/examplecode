package com.ai.sample.db.model.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author aparna
 * This table store the  mapping  between users and the roles assigned 
 * to users.
 */
@Entity
@Table(name="users_roles")
public class UsersRoles {
	/**
	 * id is unique identifier field for user roles table
	 * id  value is auto generated. Starts with one.
	 */
	private int id;
	
	
	/**
	 * User id from users table will be stored in this field
	 * as foreign key
	 */
	private Users user;
	
	/**
	 * Roles id from Roles table will be stored in this field
	 * as foreign key
	 */
	private Roles role;

	/**
	 * Default Constructor
	 */
	public UsersRoles() {
		super();
	}

	
	/** Constructor will all the field variables
	 * @param id
	 * @param user
	 * @param role
	 */
	public UsersRoles(final int id, final Users user, final Roles role) {
		super();
		this.id = id;
		this.user = user;
		this.role = role;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "UserId", nullable = false)
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "RoleId", nullable = false)
	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((user.getUserName() == null) ? 0 : user.getUserName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UsersRoles))
			return false;
		UsersRoles other = (UsersRoles) obj;
		if (id != other.id)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsersRoles [id=" + id + ", user=" + user + ", role=" + role
				+ "]";
	}

}
