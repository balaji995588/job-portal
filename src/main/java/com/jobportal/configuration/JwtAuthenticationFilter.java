package com.jobportal.configuration;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jobportal.entities.UserEntity;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.service.AuthService;
import com.jobportal.util.ErrorKeyConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String ACCESS_TOKEN_TYPE = "access";

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthService authService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken = request.getHeader(AUTHORIZATION_HEADER);

		try {
			if (requestToken != null && requestToken.startsWith("Bearer ")) {
				String accessToken = requestToken.substring(7);
				String username = jwtTokenUtil.getUsernameFromToken(accessToken);

				if (jwtTokenUtil.getTokenType(accessToken).equals(ACCESS_TOKEN_TYPE)) {
					Optional<UserEntity> user = Optional.ofNullable(authService.userInformation(username));
					user.orElseThrow(
							() -> new ResourceNotFoundException(ErrorKeyConstants.USER_E031101, "User not found"));

					if (SecurityContextHolder.getContext().getAuthentication() == null) {
						UserDetails userDetails = authService.loadUserByUsername(username);
						boolean isTokenValid = jwtTokenUtil.validateToken(accessToken, userDetails);

						if (isTokenValid) {
							UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());

							WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
							usernamePasswordAuthenticationToken
									.setDetails(webAuthenticationDetailsSource.buildDetails(request));
							SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
						}
					}
				}
			}
		} catch (ExpiredJwtException e) {

			response.setHeader("Error", "JWT Expired");
		} catch (MalformedJwtException e) {

			logger.error("Invalid JWT");
		} catch (Exception e) {

			logger.error(e.getMessage());
		}

		filterChain.doFilter(request, response);
	}

}
