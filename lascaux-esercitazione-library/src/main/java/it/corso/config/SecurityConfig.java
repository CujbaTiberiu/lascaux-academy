package it.corso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
   public AuthenticationManager authentication(AuthenticationConfiguration authentictionConfiguration) throws Exception {
		return authentictionConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
				.requestMatchers("/auth","/auth/**").permitAll()
				.requestMatchers("/book","/book/**").authenticated()
				.requestMatchers("/loan","/loan/**").authenticated()
				.requestMatchers("/review","/review/**").authenticated()
				.requestMatchers("/user","/user/**").authenticated()
				.requestMatchers("/writer","/writer/**").authenticated())
				.exceptionHandling(exception -> exception
                 .authenticationEntryPoint(jwtAuthEntryPoint))
			     .sessionManagement( session -> session
			                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
			 
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
}
