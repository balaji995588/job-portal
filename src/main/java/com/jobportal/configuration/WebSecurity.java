package com.jobportal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jobportal.service.AuthService;
import com.jobportal.util.ApiUrls;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurity {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private AuthService authService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().requestMatchers(ApiUrls.BYPASSEDURLS).permitAll().anyRequest()
				.authenticated().and().exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.authenticationProvider(authenticationProvider());
		DefaultSecurityFilterChain build = http.build();
		return build;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(authService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
//		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//
//	}
//
//	@Bean
//	public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
//			ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
//			EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
//			WebEndpointProperties webEndpointProperties, Environment environment) {
//		List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
//		Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//		allEndpoints.addAll(webEndpoints);
//		allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//		allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//		String basePath = webEndpointProperties.getBasePath();
//		EndpointMapping endpointMapping = new EndpointMapping(basePath);
//		boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment,
//				basePath);
//		return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
//				corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath),
//				shouldRegisterLinksMapping, null);
//	}
//
//	private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment,
//			String basePath) {
//		return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath)
//				|| ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
//	}
//
//	@SuppressWarnings("deprecation")
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("*");
//			}
//		};
//	}

}
