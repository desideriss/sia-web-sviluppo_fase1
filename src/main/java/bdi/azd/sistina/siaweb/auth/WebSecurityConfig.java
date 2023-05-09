package bdi.azd.sistina.siaweb.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * Gestione dells sicurezza di Spring Boot tramite JWT Token
 *
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Value("${environment}")
	private String environment;
	
	@Bean
	AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} 

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	if(environment.equalsIgnoreCase("prod") || environment.equalsIgnoreCase("simulaLdap")) {
        	//http.cors().and().csrf().disable() // Disabilita CORS e CSRF
    		http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() // Gestione utenti non autorizzati
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // Sessione Stateless
                    .authorizeRequests().antMatchers(HttpMethod.GET, "/auth/getUser").permitAll() // Url pubbliche
                    .antMatchers(HttpMethod.POST, "/auth/getUserJWT").permitAll()
                    .antMatchers("/actuator/health").permitAll()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/v3/**").permitAll()
                    .anyRequest().authenticated(); // Tutte le altre URL sono soggette ad autenticazione
            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Filtro delle URL per il JWT Token
            return http.build();    		
    	}else {
        	http.cors().and().csrf().disable() // Disabilita CORS e CSRF
        	.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() // Gestione utenti non autorizzati
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // Sessione Stateless
                    .authorizeRequests().antMatchers("/**").permitAll()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .anyRequest().authenticated(); // Tutte le altre URL sono soggette ad autenticazione
            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Filtro delle URL per il JWT Token
            return http.build();    		
    	}
    }
}
