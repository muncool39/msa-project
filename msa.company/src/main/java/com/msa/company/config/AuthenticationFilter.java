package com.msa.company.config;

import com.msa.company.config.dto.UserDetailImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		final Long userId = Long.valueOf(request.getHeader("X-User-Id"));
		final String role = request.getHeader("X-User-Role");

		UserDetails userDetails = new UserDetailImpl(userId, role);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			userDetails, null, userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);

	}
}
