package com.gsugambit.partydjserver.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsugambit.partydjserver.utils.ObjectUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(exclude = "roles")
@Entity
@Table(name = "user")
public class User implements UserDetails {

	/**
	 * UUID
	 */
	private static final long serialVersionUID = 1737644992245701637L;

	@Id
	@GenericGenerator(name = "uuid-generator", strategy = "com.gsugambit.partydjserver.utils.UUIDGenerator")
	@GeneratedValue(generator = "uuid-generator")
	@Column(name = "user_id")
	private String userId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private boolean enabled;

	@Column(name = "first_name")
	private String firstName;
	private String gender;
	@Column(name = "last_name")
	private String lastName;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false, unique = true)
	private String username;

	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (roles == null) {
			roles = new HashSet<Role>();
		}

		return roles;
	}

	public String getFullName() {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isNotEmpty(firstName)) {
			builder.append(firstName).append(" ");
		}

		if (StringUtils.isNotEmpty(lastName)) {
			builder.append(lastName);
		}

		return builder.toString().trim();
	}

	
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return ObjectUtils.toString(this);
	}

}
