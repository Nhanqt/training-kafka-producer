package com.amaris.utils.filter;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static int getUserId() {
         CustomUserDetail userDetail= (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication();
        return userDetail.getId();
    }
}
