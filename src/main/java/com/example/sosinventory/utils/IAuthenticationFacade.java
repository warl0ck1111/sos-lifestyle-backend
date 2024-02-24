package com.example.sosinventory.utils;


import com.example.sosinventory.appuser.AppUser;
import org.springframework.security.core.Authentication;


public interface IAuthenticationFacade {
    Authentication getAuthentication();
    AppUser getCurrentUser();

}