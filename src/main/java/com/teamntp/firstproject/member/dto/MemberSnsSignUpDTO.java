package com.teamntp.firstproject.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Map;

import static com.teamntp.firstproject.constants.Regexp.REGEXP_MOBILE_NO;
import static com.teamntp.firstproject.constants.Regexp.REGEXP_NAME;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSnsSignUpDTO {
    private Long memberIdx;

    private String loginId;

    private String password;

    private String path;

    private String type;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = REGEXP_NAME, message = "이름을 확인해주세요.")
    private String name;

    @NotBlank(message = "휴대전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = REGEXP_MOBILE_NO, message = "휴대전화번호를 확인해주세요.")
    private String mobileNo; // 휴대전화번호

    private String email;

    private Long zipcode; // 우편번호

    private String address; // 주소

    private String detailAddress;// 상세주소
}
