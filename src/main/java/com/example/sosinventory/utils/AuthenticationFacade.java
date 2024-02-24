package com.example.sosinventory.utils;

import com.example.sosinventory.appuser.AppUser;
import com.example.sosinventory.appuser.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public AppUser getCurrentUser(){
        try {
            return (AppUser) getAuthentication().getPrincipal();
        }catch (Exception e){
            throw new UserNotFoundException("must be logged in to perform operation;");
        }
    }
}
