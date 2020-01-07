package com.xmsy.framework.registrycenter;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("zxyy")
					.password(new BCryptPasswordEncoder().encode("zxyy")).roles("USER");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
			http.csrf().disable();
			super.configure(http);
		}
}
