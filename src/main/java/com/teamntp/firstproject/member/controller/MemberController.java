package com.teamntp.firstproject.member.controller;

import com.teamntp.firstproject.member.dto.MemberSignUpDTO;
import com.teamntp.firstproject.member.dto.MemberSnsSignUpDTO;
import com.teamntp.firstproject.member.service.MemberService;
import com.teamntp.firstproject.security.dto.AuthMemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Log4j2
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public void gotoLogin() {
        log.info("/login.........................................");
    }

    ///////////////////////폼 회원 가입 처리///////////////////////

    @GetMapping("/sign") // 폼 회원 유형 GET
    public void gotoSign(MemberSignUpDTO memberSignUpDTO) {
        log.info("/sign....................................");
        log.info("MemberSignUpDTO" + memberSignUpDTO);
    }

    @GetMapping("/signup") // 폼 회원 가입 GET
    public void gotoSignup(MemberSignUpDTO memberSignUpDTO, BindingResult bindingResult) {
        log.info("/signup.......................");
        log.info("MemberSignUpDTO: " + memberSignUpDTO);
        log.info("type: " + memberSignUpDTO.getType());
    }

    @PostMapping("/signup") // 폼 회원 가입 POST
    public String createMember(@Valid MemberSignUpDTO memberSignUpDTO
            , BindingResult bindingResult) {
        log.info("/signupPost.................................");
        log.info("MemberSignUpDTO" + memberSignUpDTO);

        if (bindingResult.hasErrors()) {
            return "/signup";
        }

        if (!memberSignUpDTO.passwordChecker(memberSignUpDTO)) {
            bindingResult.rejectValue("passwordCheck", "passwordInCorrect",
                    "비밀번호가 일치하지 않습니다.");
            return "/signup";
        }

        log.info("MemberSignUpDTO: " + memberSignUpDTO);


        try {
            // 서비스 호출 후 가입 처리
            memberService.createMember(memberSignUpDTO);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/signup";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/signup";
        }

        return "redirect:/login";
    }

    ///////////////////////소셜 회원 가입 처리///////////////////////

    @GetMapping("/snsSign") // 소셜 회원 유형 GET
    public void gotoSnsSign(@ModelAttribute MemberSnsSignUpDTO memberSnsSignUpDTO, HttpServletRequest request) {
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) request.getSession().getAttribute("authMemberDTO");
        memberSnsSignUpDTO.setMemberIdx(authMemberDTO.getMemberIdx());
        memberSnsSignUpDTO.setLoginId(authMemberDTO.getLoginId());
        memberSnsSignUpDTO.setEmail((String) authMemberDTO.getAttr().get("email"));
        log.info("/snsSign....................................");
        log.info("AuthMemberDTO" + memberSnsSignUpDTO);
    }

    @GetMapping("/snsSignup") // 소셜 회원 가입 GET
    public void gotoSnsSignup(MemberSnsSignUpDTO memberSnsSignUpDTO) {
        log.info("/signup.......................");
        log.info("MemberSignUpDTO: " + memberSnsSignUpDTO);
    }

    @PostMapping("/snsSignup") // 소셜 회원 가입 POST
    public String createSnsMember(@Valid MemberSnsSignUpDTO memberSnsSignUpDTO
            , BindingResult bindingResult) {
        log.info("MemberSnsSignUpDTO: " + memberSnsSignUpDTO);

        if (bindingResult.hasErrors()) {
            return "/snsSignup";
        }

        // google 로그인의 경우 비밀번호를 확인하지 않아도 됨.
        log.info("MemberSignUpDTO: " + memberSnsSignUpDTO);


        try {
            // 서비스 호출 후 가입 처리
            memberService.createMemberFromSocial(memberSnsSignUpDTO);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/snsSignup";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/snsSignup";
        }

        return "redirect:/login";
    }
}
