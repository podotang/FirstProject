package com.teamntp.firstproject.member.service;

import com.teamntp.firstproject.member.dto.MemberSignUpDTO;
import com.teamntp.firstproject.member.dto.MemberSnsSignUpDTO;
import com.teamntp.firstproject.member.entity.Member;
import com.teamntp.firstproject.member.entity.MemberRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public interface MemberService {

    void createMember(MemberSignUpDTO memberSignUpDTO); // member 객체 생성 후 save 처리

    void createMemberFromSocial(MemberSnsSignUpDTO memberSnsSignUpDTO); // 소셜 회원 가입 처리

    default Member dtoToEntity(MemberSignUpDTO memberSignUpDTO) {
        String type = memberSignUpDTO.getType();
        Member member = Member.builder()
                .loginId(memberSignUpDTO.getLoginId())
                .password(memberSignUpDTO.getPassword())
                .path(memberSignUpDTO.getPath())
                .type(memberSignUpDTO.getType())
                .name(memberSignUpDTO.getName())
                .mobileNo(memberSignUpDTO.getMobileNo())
                .email(memberSignUpDTO.getEmail())
                .zipcode(memberSignUpDTO.getZipcode())
                .address(memberSignUpDTO.getAddress())
                .detailedAddress(memberSignUpDTO.getDetailAddress())
                .build();

        // 권한 setting
        member.addMemberRole(MemberRole.MEMBER);

        if(type.equals("teacher")) {
            member.addMemberRole(MemberRole.TEACHER);
        } else if (type.equals("student")) {
            member.addMemberRole(MemberRole.STUDENT);
        }

        return member;
    }

    default Member dtoToEntityForSocial(MemberSnsSignUpDTO memberSnsSignUpDTO) {
        String type = memberSnsSignUpDTO.getType();
        Member member = Member.builder()
                .memberIdx(memberSnsSignUpDTO.getMemberIdx())
                .loginId(memberSnsSignUpDTO.getLoginId())
                .password(new BCryptPasswordEncoder().encode("@@SIGNUP_COMPLETE@@"))
                .path(memberSnsSignUpDTO.getPath())
                .type(memberSnsSignUpDTO.getType())
                .name(memberSnsSignUpDTO.getName())
                .mobileNo(memberSnsSignUpDTO.getMobileNo())
                .email(memberSnsSignUpDTO.getEmail())
                .zipcode(memberSnsSignUpDTO.getZipcode())
                .address(memberSnsSignUpDTO.getAddress())
                .detailedAddress(memberSnsSignUpDTO.getDetailAddress())
                .build();

        // 권한 setting
        member.addMemberRole(MemberRole.MEMBER);
        if(type.equals("teacher")) {
            member.addMemberRole(MemberRole.TEACHER);
        } else if (type.equals("student")) {
            member.addMemberRole(MemberRole.STUDENT);
        }
        return member;
    }
}
