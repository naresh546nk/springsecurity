package com.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.service.UserService;
import com.jwt.utility.JwtUtilltiy;


@Component
//@Slf4j
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtilltiy jwtUtilltiy;
	
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("calling doFilterInternal");
		String authorization= request.getHeader("Authorization");
		String token=null;
		String userName=null;
		
		if(authorization!=null && authorization.startsWith("Bearer") ) {
			token=authorization.substring(7);
			System.out.println("token : "+token);
			userName=jwtUtilltiy.getUsernameFromToken(token);
			System.out.println("username: "+userName);
		}
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=userService.loadUserByUsername(userName);
			System.out.println("userdetails:"+userDetails);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new
					UsernamePasswordAuthenticationToken( userDetails, null,userDetails.getAuthorities());
			
			usernamePasswordAuthenticationToken.setDetails(new
					WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			
		}
		System.out.println("end do fileter ");
		filterChain.doFilter(request, response);

	}

}
