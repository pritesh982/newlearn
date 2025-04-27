package com.sharing.doc_sharing.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectURL = "/dashboard";

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMIN":
                    redirectURL = "/admin/dashboard";
                    break;
                case "ROLE_TEACHER":
                    redirectURL = "/teacher/dashboard";
                    break;
                case "ROLE_STUDENT":
                    redirectURL = "/student/dashboard";
                    break;
                default:
                    redirectURL = "/dashboard";
                    break;
            }
        }

        response.sendRedirect(redirectURL);
    }
}
