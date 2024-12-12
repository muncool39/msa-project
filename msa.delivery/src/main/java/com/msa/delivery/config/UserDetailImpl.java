package com.msa.delivery.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.msa.delivery.domain.entity.enums.UserRole;

public record UserDetailImpl(
	String userId,
	String role

) implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return userId; // user pk
	}

	public UserRole getUserRole() {
		return UserRole.fromString(role);
	}
}
