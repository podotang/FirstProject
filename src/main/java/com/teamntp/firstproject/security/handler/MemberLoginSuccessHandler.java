package com.teamntp.firstproject.security.handler;

import com.teamntp.firstproject.security.dto.AuthMemberDTO;
import com.teamntp.firstproject.security.utils.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.List;


@Log4j2
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {
    // RedirectStrategy(인터페이스) -> DefaultRedirectStrategy(구현 클래스)
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // PasswordEncoder 주입 -> 소셜 로그인한 비밀번호가 "@@GOOGLE@@"인 경우 회원 정보를 변경할 수 있도록 하기 위헤서
    private PasswordEncoder passwordEncoder;

    // 생성자
    public MemberLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("========================================================");
        log.info("on AuthenticationSuccess ");

        // authentication 정보 가져오기
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();

        log.info("=========================================================");
        log.info("authMemberDTO -> " + authMemberDTO);
        log.info("authentication.getDetails().toString() -> " + authentication.getAuthorities().toString());

        // 자체회원(form)인지 구글회원(google) 확인할 때 필요한 변수들
        String path = authMemberDTO.getPath(); // form(자체), google(구글)
        boolean passwordChecker = passwordEncoder.matches("@@GOOGLE@@", authMemberDTO.getPassword());

        log.info("This Member is from " + path);


        // session 에 AuthMemberDTO 담기
//        HttpSession session = request.getSession();
//        session.setAttribute("authMemberDTO", authMemberDTO);


        // 권한 정보 List<String> 에 담아서 가져오기
        List<String> authList = SecurityUtils.getAuthList(authentication);

        // form 회원인지 google 회원인지 구분 후 권한에 따라 페이지 분기
        if (path.equals("form")) { // 자체 회원
            if (authList.contains("ROLE_ADMIN")) {
                redirectStrategy.sendRedirect(request, response, "/admin");
            } else if (authList.contains("ROLE_MEMBER")) {
                redirectStrategy.sendRedirect(request, response, "/member/board/list");
            }

        } else if(path.equals("google")) {
            if(!passwordChecker) {
                if (authList.contains("ROLE_ADMIN")) {
                    redirectStrategy.sendRedirect(request, response, "/admin");
                } else if (authList.contains("ROLE_MEMBER")) {
                    redirectStrategy.sendRedirect(request, response, "/member/board/list");
                }
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("authMemberDTO", authMemberDTO);
                redirectStrategy.sendRedirect(request, response, "/snsSign?path=google");
            }

        }
    }
}
