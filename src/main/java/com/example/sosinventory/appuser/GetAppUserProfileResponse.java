package com.example.sosinventory.appuser;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class GetAppUserProfileResponse {

    private Long id;

    private String firstName;
    private String lastName;

    private String username;

    private String email;


    private String phoneNumber;

    private String role;


    private LocalDateTime dateOfBirth;

    private String profilePhotoUrl;

    private String gender;



    private Boolean locked = false;
    private Boolean enabled = false;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime lastLogin = LocalDateTime.now();


    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeCreated;

    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeUpdated;
}
