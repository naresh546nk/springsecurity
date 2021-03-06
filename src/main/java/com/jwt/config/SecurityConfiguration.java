package com.jwt.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.filter.JwtFilter;
import com.jwt.service.UserService;



@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	

    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;
    
    @Autowired
    private PasswordEncoder bycrEncoder;


    
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////		 configure AuthenticationManager so that it knows from where to load
////		 user for matching credentials
////		 Use BCryptPasswordEncoder
//		auth.userDetailsService(userService);
//	}

    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//log.info("calling configure(AuthenticationManagerBuilder auth)");
        auth.userDetailsService(userService);
    }

   
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//log.info("calling : configure(HttpSecurity http)");
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/authenticate")
                .permitAll()
                .antMatchers("/user")
                .permitAll()
                .antMatchers("/test")
                .permitAll()
                .antMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }
    

    
}
