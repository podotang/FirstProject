package com.teamntp.firstproject.member.service;

import com.teamntp.firstproject.common.DataNotFoundException;
import com.teamntp.firstproject.member.dto.MemberSignUpDTO;
import com.teamntp.firstproject.member.dto.MemberSnsSignUpDTO;
import com.teamntp.firstproject.member.entity.Member;
import com.teamntp.firstproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createMember(MemberSignUpDTO memberSignUpDTO) {
        String password = memberSignUpDTO.getPassword();

        // 비밀번호 암호화
        memberSignUpDTO.setPassword(passwordEncoder.encode(password));

        // DB에 INSERT
        Member member = dtoToEntity(memberSignUpDTO);
        memberRepository.save(member);
    }

    @Override
    public void createMemberFromSocial(MemberSnsSignUpDTO memberSnsSignUpDTO) {
        Member member = dtoToEntityForSocial(memberSnsSignUpDTO);
        memberRepository.save(member);

    }

    // 로그인 아이디로 멤버 엔티티 객체 가져오기
    public Member getMember(String loginId) {
        Optional<Member> member = memberRepository.findByLoginId(loginId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }
}
