package com.example.sosinventory.appuser;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAppUsersEmailRequest {
    private String oldEmail;
    private String newEmail;
}
