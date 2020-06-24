package com.akr.config;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.akr.utils.Constants;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

		@Autowired
		DataSource dataSource;
		
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.jdbcAuthentication()
	        .dataSource(dataSource);
	    }
	    
	    @Override
		public void configure(WebSecurity web) throws Exception {
	    	web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
		}
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	http.authorizeRequests()
	        .antMatchers("/h2-console/**").permitAll()
	    	.and()
	    	.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/register").permitAll()
			.antMatchers("/seller").hasAnyAuthority(Constants.SELLER)
			.antMatchers("/user").hasAnyAuthority(Constants.USER, Constants.SELLER)
			.anyRequest().authenticated()
			.and()
			.csrf().disable() //rest of the configs below according to your needs.
		    .headers().frameOptions().disable()
		    .and()
			// form login
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/user")
			.failureUrl("/login?error=true")
			.usernameParameter("username")
			.passwordParameter("password")
			.and()
			// logout
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login").and()
			.exceptionHandling()
			.accessDeniedPage("/access-denied");
	
	    }
	    
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    
}
