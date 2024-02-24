package com.example.sosinventory.appuser;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Okala Bashir .O.
 */

@Data
@NoArgsConstructor
public class UpdateAppUserProfileRequest {

    @NotBlank(message = "first name ground can not be empty")
    private String firstName;

    @NotBlank(message = "last name ground can not be empty")
    private String lastName;

    @NotBlank(message = "username ground can not be empty")
    private String username;

    @NotBlank(message = "phone Number ground can not be empty")
    private String phoneNumber;


    private LocalDateTime dateOfBirth;

    private String gender;


}
