package com.example.sosinventory.appuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Okala Bashir .O.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String password;
}
