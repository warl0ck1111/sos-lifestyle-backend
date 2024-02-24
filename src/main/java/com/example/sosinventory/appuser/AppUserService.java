package com.example.sosinventory.appuser;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Okala Bashir .O.
 */

public interface AppUserService extends UserDetailsService {


    GetAppUserProfileResponse getAppUserProfile(long userId);

    @Transactional
    AuthenticationResponse registerAppUser(RegisterUserRequest userRequest);

    AuthenticationResponse loginAppUser(AuthenticationRequest request);


//    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    @Transactional
    void updateAppUserProfile(String userId, UpdateAppUserProfileRequest request);

    Page<AppUser> findAllUsers(int page, int pageSize, String sortField, Sort.Direction sortDirection);


    @Transactional
    void enableAppUser(String email);

    @Transactional
    void disableAppUser(String email);

    @Transactional
    void lockAppUser(String email);

    @Transactional
    void unlockAppUser(String email);

    @Transactional
    AuthenticationResponse activateAppUserAccount(String email, String token);

    @Transactional
    void forgotPassword(String email);

    @Transactional
    void resetAppUserPassword(ResetPasswordRequest request);


    @Transactional
    void enableAppUserByEmail(String email);


    void disableAppUserByEmail(String email);


//    String verifyAppUsersKyc(String idNumber, VerificationType verificationType, AppUserVerificationRequest verificationRequest);

    void updateUserProfile();

    Page<AppUser> searchUsers(String keyword, int page, int pageSize, String sortField, Sort.Direction sortDirection);
}
