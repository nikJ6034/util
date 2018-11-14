package com.line.kepco.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.line.kepco.module.user.service.CustomUserDetailsService;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = authentication.getName(); 
		String password = (String) authentication.getCredentials();
		
		UserDetails loadUserByUsername = customUserDetailsService.loadUserByUsername(username);
		
//		if (!passwordEncoder.matches(password, loadUserByUsername.getPassword())) {
//		      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//		    }
		
//		if(!StringUtils.equals(loadUserByUsername.getUsername(), username)) {
//			throw new BadCredentialsException("존재하지 않는 아이디입니다.");
//		}
		
		return new UsernamePasswordAuthenticationToken(loadUserByUsername, password, loadUserByUsername.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
