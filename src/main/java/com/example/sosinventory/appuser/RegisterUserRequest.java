package com.example.sosinventory.appuser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Okala Bashir .O.
 */

@Data
@NoArgsConstructor
public class RegisterUserRequest {
    @NotBlank(message = "firstName can not be blank")
    private String firstName;

    @NotBlank(message = "lastname can not be blank")

    private String lastName;

    @NotBlank(message = "username can not be blank")
    private String username;

    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "invalid password")
    private String password;

    private String role;


}
