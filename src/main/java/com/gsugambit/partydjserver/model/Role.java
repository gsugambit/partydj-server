package com.gsugambit.partydjserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsugambit.partydjserver.utils.ObjectUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority{

	/**
	 * UUID
	 */
	private static final long serialVersionUID = 8893160247369512674L;

	@Id
	@GenericGenerator(name = "uuid-generator", 
	strategy = "com.gsugambit.partydjserver.utils.UUIDGenerator")
	@GeneratedValue(generator = "uuid-generator")
	@Column(name = "role_id")
	private String roleId;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	public String getAuthority() {
		return name;
	}
	
	@Override
	public String toString() {
		return ObjectUtils.toString(this);
	}
}
