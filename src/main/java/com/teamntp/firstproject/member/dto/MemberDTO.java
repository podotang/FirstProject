package com.teamntp.firstproject.member.dto;

import com.teamntp.firstproject.member.entity.Member;
import com.teamntp.firstproject.member.entity.MemberRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
public class MemberDTO {

    private Long memberIdx; // pk index

    private String loginId; // 자체회원: 회원이 설정한 아이디, 구글회원: google-sub

    private String password; // 비밀번호

    private LocalDate withdrawalDate; // 탈퇴일

    private String path; // form, google

    private String type; // student, teacher

    @Builder.Default // 임시 처리
    private Long accountStatus = 30002L; // 30002(승인)으로 처리

    // 권한 컬럼
    // 임시 선생님 회원가입
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private final Set<MemberRole> roleSet = new HashSet<>(); // 권한 컬럼

    private String name; // 이름

    private String mobileNo; // 연락처

    private String email; // 이메일

    private Long zipcode; // 우편번호

    private String address; // 주소

    private String detailedAddress;// 상세주소


    // Member 권한 추가 method
    public Set<MemberRole> addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
        return roleSet;
    }

    private Long studentIdx;

    public static MemberDTO toDTO(Member entity) {
        return MemberDTO.builder()
                .memberIdx(entity.getMemberIdx())
                .name(entity.getName())
                .build();
    }
}
