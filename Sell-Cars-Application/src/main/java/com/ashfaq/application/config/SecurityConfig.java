package com.ashfaq.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomJWTAuthenticationFilterChain jwtauthenticationFilterChain;
	
	
	@Autowired
	private CustomAppUserDetailService customAppUserDetailService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
//		CSRF 
//		http.csrf(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());
		http.formLogin(Customizer.withDefaults());
		
		http.httpBasic(Customizer.withDefaults());
		
		
		
		 http.authenticationProvider(authenticationProvider());
		
		http.authorizeHttpRequests(requests -> {
//				.requestMatchers("/sellers/**").permitAll()
//				.requestMatchers("/buyers/**").permitAll()
//				.requestMatchers("/welcome/**").permitAll()
			requests.requestMatchers("/welcome/**").permitAll();
			
			requests.requestMatchers("/register/**").permitAll();
			//Buyer
			requests.requestMatchers("/buyers/**").hasAnyRole("USER");
			
//			Seller
			requests.requestMatchers("/sellers/**").hasAnyRole("SELLER","ADMIN");
			
			
//			admin
			requests.requestMatchers("/admin/**").hasRole("ADMIN");

			
			requests.anyRequest().authenticated();
		}
		);
		
//		http.addFilterBefore(JWTauthenticationFilterChain, null)
		
		//JWT authentication filter
		http.addFilterBefore(jwtauthenticationFilterChain, UsernamePasswordAuthenticationFilter.class);//our JWT customfilter should be called before UsernamepasswordAuthenticationFilter class

		return http.build();
		
		
	}
////	 in memory USER
//		@Bean
//		public UserDetailsService userDetailsServiceInMem() {
////			
////			
//			UserDetails normalUser =User.builder()
//					.username("buyer")
//					.password(bCryptPasswordEncoder().encode("password"))
//					.roles("USER")
//					.build();
//			
//			UserDetails sellerUser =User.builder()
//					.username("seller")
//					.password(bCryptPasswordEncoder().encode("password"))
//					.roles("USER","SELLER")
//					.build();
//			
//			
////			
//			UserDetails adminUser = User.builder()
//					.username("ashfaq")
//					.password(bCryptPasswordEncoder().encode("password"))
//					.roles("ADMIN","USER","SELLER")
//					.build();
////			
//			return new InMemoryUserDetailsManager(normalUser,adminUser,sellerUser);
//		}
		
//		
//		@Bean
//		public UserDetailsService userDetailsService() {
//			
//			return customAppUserDetailService;
//		}
//		
	
		@Bean
		public PasswordEncoder bCryptPasswordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
//		@Bean
		public AuthenticationProvider authenticationProvider() {

//			dataaccessobject DAO this is authentication provider for DB 
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(customAppUserDetailService);
			provider.setPasswordEncoder(bCryptPasswordEncoder());
			return provider;
		}
		
		
		// JWT process for generating token
		@Bean
		public AuthenticationManager authenticationManager() {
			return new ProviderManager(authenticationProvider());
		}	
		
}
