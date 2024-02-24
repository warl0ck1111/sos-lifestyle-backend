package com.example.sosinventory.utils.mapper;

import com.example.sosinventory.appuser.AppUser;
import com.example.sosinventory.appuser.AppUserPlayerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {


    AppUserPlayerResponse appUserToAppUserClientResponse(AppUser appUser);
}
