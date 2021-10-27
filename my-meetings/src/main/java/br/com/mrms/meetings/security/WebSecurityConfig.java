package br.com.mrms.meetings.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PATHS = new String[] { "/meeting/**", "/meeting-category/**", "/user/**" };

	/**
	 * Gera um hash novo para a mesma senha
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	/**
	 * Autenticação
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
		.withUser("mrms").password(passwordEncoder().encode("password")).roles("USER");
	}

	/**
	 * Autorização
	 * csrg = desabilita pois app não mantem estado
	 * cors = proteção que impede acesso de origens diferentes
	 * SessionCreationPolicy.STATELESS = não utiliza estado da sessão
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests()
		.antMatchers("/api/auth/**").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers(HttpMethod.POST,PATHS).hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,PATHS).hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,PATHS).hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,PATHS).hasAnyRole("ADMIN","USER")
		.anyRequest().authenticated()
		.and().httpBasic();
	}

}
