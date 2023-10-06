package com.teamntp.firstproject.security.service;

import com.teamntp.firstproject.member.entity.Member;
import com.teamntp.firstproject.member.repository.MemberRepository;
import com.teamntp.firstproject.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("MemberUserDetailService loadUserByUsername " + loginId);

        // 회원 정보가 있는지 조회(자체회원: form)
        Optional<Member> result = memberRepository.findByLoginIdWithPath(loginId, "form");

        // 없으면 throw exception
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("아이디를 확인해주세요.");
        }

        // 회원 정보를 가져옴
        Member member = result.get();

        log.info("--------------------------------------------");
        log.info(member);

        // AuthMemberDTO 로 변환
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getLoginId()
                , member.getPassword()
                , member.getPath()
                , member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority
                ("ROLE_" + role.name())).collect(Collectors.toSet())
        );

        authMemberDTO.setName(member.getName());
        authMemberDTO.setPath(member.getPath());
        authMemberDTO.setType(member.getType());
        authMemberDTO.setMemberIdx(member.getMemberIdx());

        return authMemberDTO;
    }

}
