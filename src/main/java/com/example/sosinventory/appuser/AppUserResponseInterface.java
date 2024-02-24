package com.example.sosinventory.appuser;

import java.time.LocalDateTime;

public interface AppUserResponseInterface {

    Long getUserId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getUsername();

    String getPhoneNumber();

    String getAddress();

    String getCountryId();

    String getUserType();

    Boolean getIsKycVerified();

    LocalDateTime getDateOfBirth();

    String getProfilePhoto();

    Gender getGender();

    String getTitle();

    String getRoleId();


}
