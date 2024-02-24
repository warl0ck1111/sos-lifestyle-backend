package com.example.sosinventory.appuser;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author Okala Bashir .O.
 */

@Data
@NoArgsConstructor
public class AppUserResponse {

    private Long id;
    @NotBlank(message = "first name ground can not be empty")
    private String firstName;

    @NotBlank(message = "last name ground can not be empty")
    private String lastName;


    @NotBlank(message = "email can not be empty")
    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "username ground can not be empty")
    private String username;

    @NotBlank(message = "phone Number ground can not be empty")
    private String phoneNumber;

//    private String address;

    private String countryId;

    private String userType;

    private Boolean isKycVerified = Boolean.FALSE;

    private LocalDateTime dateOfBirth;

    private String profilePhoto;

    private Gender gender;

    private String title;

    private String roleId;
    private Boolean locked = false;
    private Boolean enabled = false;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime lastLogin = LocalDateTime.now();


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeUpdated;


}
