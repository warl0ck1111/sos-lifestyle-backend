package com.example.sosinventory.appuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String role;
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;
    private String emailAddress;
    private String accessToken;
    private String refreshToken;
}
