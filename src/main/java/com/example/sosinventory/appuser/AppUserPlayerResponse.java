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
public class AppUserPlayerResponse {

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

}
