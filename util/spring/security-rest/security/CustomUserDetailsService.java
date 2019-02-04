package com.eum.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eum.member.entity.Member;
import com.eum.member.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Member> member = memberRepository.findByMemberId(username);
		
		if(member.isPresent()) {
			return new SecurityMember(member.get());
		}else {
			return null;
		}
	}

}
