package com.teamntp.firstproject.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SecurityUtils {

    public static List<String> getAuthList(Authentication authentication) { // 회원 권한 List<String>로 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> auth = authorities.stream().iterator();
        List<String> authList = new ArrayList<>();
        while (auth.hasNext()) {
            String strAuth = auth.next().toString();
            authList.add(strAuth);
        }

        return authList;
    }
}
