package com.morocco.mpt.Controller.users;

import com.morocco.mpt.domain.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

//@SuppressFBWarnings("EI_EXPOSE_REP2")
@RequiredArgsConstructor
@ControllerAdvice
public class UserControllerAdvice {

    @ModelAttribute("caller")
    public Users user(Authentication auth) {
        if (auth != null && auth.getPrincipal() instanceof Users) {
            return (Users) auth.getPrincipal();
        }
        return null;
    }
}

