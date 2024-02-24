package com.example.sosinventory.utils;

import com.example.sosinventory.appuser.AppUser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@UtilityClass
@Slf4j
public class AppUserUtils {
    public String getAppUserFullName(AppUser appUser) {
        if (Objects.isNull(appUser)) {
            log.error("app user object is null");
            throw new IllegalArgumentException("app user object is NULL");
        } else {
            return appUser.getFirstName() + " " + appUser.getLastName();
        }
    }
}
