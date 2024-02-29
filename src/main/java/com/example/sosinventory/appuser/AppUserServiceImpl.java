package com.example.sosinventory.appuser;


import com.example.sosinventory.config.JwtService;
//import com.example.sosinventory.email.UserEmailService;
import com.example.sosinventory.token.TokenService;
import com.example.sosinventory.token.TokenType;
import com.example.sosinventory.utils.Language;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Okala Bashir .O.
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;


    @Override
    public GetAppUserProfileResponse getAppUserProfile(long userId) {
        log.info("getAppUserProfile/userId: {}", userId);
        if (userId > 0) {
            AppUser user = findUserById(userId);
            GetAppUserProfileResponse userProfileResponse = new GetAppUserProfileResponse();
            BeanUtils.copyProperties(user, userProfileResponse);
            return userProfileResponse;
        } else {
            log.info("getAppUserProfile/invalid user id");
            throw new AppUserException(String.format("invalid user id: %s", userId));
        }
    }

    @Override
    @Transactional
    public AuthenticationResponse registerAppUser(RegisterUserRequest userRequest) {
        log.info("registerAppUser/userRequest: {}", userRequest);
        try {
            if (userRequest != null) {
                //check if user exists
                Boolean userExists = appUserRepository.existsByUsername(userRequest.getUsername());
                if (userExists) {
                    log.error("user with username {} already exists", userRequest.getUsername());
                    throw new AppUserException(String.format("user with username: %s already exists", userRequest.getUsername()));
                }
                else { // create new user

                    String encodedPassword = passwordEncoder.encode((userRequest.getPassword()));
                    AppUser user = computeAppUserData(userRequest, encodedPassword, Language.English);

                    AppUser registeredUser = appUserRepository.save(user);
                    log.info("registerAppUser/ user {} created successfully", userRequest.getUsername());

                    //generate and save token to users email
                    final String token = tokenService.generateAndPersistUserToken(registeredUser.getEmail(), TokenType.NEW_ACCOUNT).getToken();

                    //send email todo
//                    userEmailService.constructAndSendUserRegistrationEmailMessage(registeredUser.getEmail(), token, Language.English);

                    log.info("registerAppUser/registeredUser/Email:{}, token:{}", registeredUser.getEmail(), token);

                    String jwtToken = jwtService.generateToken(registeredUser);
                    String refreshToken = jwtService.generateRefreshToken(registeredUser);
                    return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).username(registeredUser.getUsername()).userId(String.valueOf(registeredUser.getId())).role(registeredUser.getRole().name()).lastLogin(registeredUser.getLastLogin()).firstName(registeredUser.getFirstName()).lastName(registeredUser.getLastName()).emailAddress(registeredUser.getEmail()).build();
                }
            }
        } catch (Exception e) {
            log.error("registerAppUser/there was an exception: ", e);
            throw new AppUserException(e.getMessage());
        }
        return null;
    }


    public AuthenticationResponse loginAppUser(@NotNull AuthenticationRequest request) {
        final String username = request.getUsername().trim().toLowerCase();
        log.info("loginAppUser/username:{}", username);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));


        final AppUser appUser = findAppUserByUsername(request.getUsername().toLowerCase());
        if (appUser != null) {
            LocalDateTime lastLogin = appUser.getLastLogin();
            appUser.setLastLogin(LocalDateTime.now());
            appUserRepository.save(appUser);

            String jwtToken = jwtService.generateToken(appUser);
            String refreshToken = jwtService.generateRefreshToken(appUser);
//            todo:
//             revokeAllUserTokens(appUser);
//            tokenService.saveUserToken(appUser, jwtToken);

            return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).userId(String.valueOf(appUser.getId())).role(appUser.getRole().name()).lastLogin(appUser.getLastLogin()).firstName(appUser.getFirstName()).lastName(appUser.getLastName()).emailAddress(appUser.getEmail()).build();
        } else log.info("loginUser/user:{} not found", username);
        return null;
    }


    @Override
    @Transactional
    public void updateAppUserProfile(String userId, UpdateAppUserProfileRequest request) {
        log.info("updateAppUserProfile/UpdateAppUserProfileRequest: {}", request);
        try {
            if (request != null) {
                // update existing user

                AppUser existingUser = findUserById(Long.parseLong(userId));


                AppUser updatedAppUserData = computeUpdateProfileUserData(request, existingUser);
                appUserRepository.save(updatedAppUserData);

                log.info("updateAppUserProfile/ userId {} updated successfully", userId);
            }
        } catch (Exception e) {
            log.error("registerAppUser/there was an exception: ", e);
            throw new AppUserException(e.getMessage());
        }

    }

    @Override
    public Page<AppUser> findAllUsers(int page, int pageSize, String sortField, Sort.Direction sortDirection) {
        log.info("findAllUsers/page = {}", page);
        log.info("findAllUsers/pageSize = {}", pageSize);
        log.info("findAllUsers/sortField = {}", sortField);
        log.info("findAllUsers/sortDirection = {}", sortDirection);
        Sort sort = Sort.by(sortDirection, sortField);
        return appUserRepository.findAll(PageRequest.of(page, pageSize, sort));
    }


    public void updateProfileImage(long userId, String profilePhotoUrl) {
        if (Strings.isNotBlank(profilePhotoUrl)) {
            AppUser user = findUserById(userId);
            user.setProfilePhotoUrl(profilePhotoUrl);
            appUserRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("loadByUsername/username:{}", username);
        return appUserRepository.findByUsername(username.toLowerCase().trim()).orElseThrow(() -> {
            log.error(String.format("no user with username: %s found", username));
            return new AppUserException(String.format("no user with username: %s found", username));
        });
    }


    public Page<AppUserResponseInterface> findAllUsersByRole(String roleName, int page, int pageSize, String sortField, Sort.Direction sortDirection) {
        log.info("getAllUsers/roleName = {}", roleName);
        log.info("getAllUsers/page = {}", page);
        log.info("getAllUsers/pageSize = {}", pageSize);
        log.info("getAllUsers/sortField = {}", sortField);
        log.info("getAllUsers/sortDirection = {}", sortDirection);
        Sort sort = Sort.by(sortDirection, sortField);
        return appUserRepository.findAllAppUsers(PageRequest.of(page, pageSize, sort));
    }

    @Override
    @Transactional
    public void enableAppUser(String email) {
        log.info("enableAppUser/email: {}", email);
        AppUser appUser = findAppUserByEmail(email);
        if (appUser != null) {
            appUser.setEnabled(true);
            appUserRepository.save(appUser);
            log.info("enableAppUser/user enabled successfully");
        } else {
            log.info("enableAppUser/there was an issue enabling app user with email:{}", email);
        }
    }

    @Override
    @Transactional
    public void disableAppUser(String email) {
        log.info("disableAppUser/email: {}", email);
        AppUser appUser = findAppUserByEmail(email);
        appUser.setEnabled(false);
        appUserRepository.save(appUser);
        log.info("disableAppUser/user disabled successfully");

    }

    @Override
    @Transactional
    public void lockAppUser(String email) {
        log.info("lockAppUser/email: {}", email);
        AppUser appUser = findAppUserByEmail(email);
        appUser.setLocked(true);
        appUserRepository.save(appUser);
        log.info("lockAppUser/user locked successfully");
    }

    @Override
    @Transactional
    public void unlockAppUser(String email) {
        log.info("unlockAppUser/email: {}", email);
        AppUser appUser = findAppUserByEmail(email);
        appUser.setLocked(false);
        appUserRepository.save(appUser);
        log.info("unlockAppUser/user locked successfully");
    }

    @Override
    @Transactional
    public AuthenticationResponse activateAppUserAccount(String email, String token) {
        log.info("activateAppUserAccount/email:{}", email);
        log.info("activateAppUserAccount/token:{}", token);
        email = email.toLowerCase().trim();
        boolean tokenExistsForEmail = tokenService.existsByEmailAddressAndTokenAndTokenType(email, token, TokenType.NEW_ACCOUNT);
        if (tokenExistsForEmail) {
            AppUser appUser = findAppUserByEmail(email);
            appUser.setLocked(false);
            appUser.setEnabled(true);
            appUserRepository.save(appUser);
            log.info("activateAppUserAccount/ user:{} profile activated successfully", email);
            tokenService.deleteToken(token);
            log.info("activateAppUserAccount/ token:{} for user removed successfully", token);
        } else {
            throw new AppUserException(String.format("token does not exist for email: %s", email));
        }

        return null;
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        log.info("forgotPassword/email: {}", email);
        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new AppUserException(String.format("email:%s does not exist", email)));
        String token = tokenService.generateAndPersistUserToken(email, TokenType.FORGOT_PASSWORD).getToken();
//        todo:
//        userEmailService.constructAndSendResetPasswordEmailMessage(appUser.getEmail(), token, Language.English);
        log.info("forgotPassword/appUser/Email:{}, token:{}",appUser.getEmail(), token);
    }

    @Override
    @Transactional
    public void resetAppUserPassword(@NotNull ResetPasswordRequest request) {
        log.info("resetAppUserPassword/email:{}", request.getEmail());
        log.info("resetAppUserPassword/token:{}", request.getToken());


        //get token for email and token and tokenType
        boolean tokenExistForEmail = tokenService.existsByEmailAddressAndTokenAndTokenType(request.getEmail(), request.getToken(), TokenType.FORGOT_PASSWORD);
        if (tokenExistForEmail) {
            AppUser appUser = findAppUserByEmail(request.getEmail());
            String encodedPassword = passwordEncoder.encode((request.getPassword()));
            appUser.setPassword(encodedPassword);
            appUserRepository.save(appUser);
            log.info("resetAppUserPassword/ user password reset successfully");

        } else log.info("user password was not reset successfully");
    }

    @Override
    @Transactional
    public void enableAppUserByEmail(String email) {
        log.info("enableAppUserByEmail/email: {}", email);
        AppUser appUser = findAppUserByEmail(email);
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
    }

    @Transactional
    public void disableAppUserByEmail(String email) {
        log.info("disableAppUserByEmail/email={}", email);
        AppUser appUser = findAppUserByEmail(email);
        appUser.setEnabled(false);
        appUserRepository.save(appUser);
    }

    @Override
    public void updateUserProfile() {
//        todo:
    }


    @Transactional
    public void unLockAppUserByEmail(String email) {
        log.info("unLockAppUserByEmail/email={}", email);
        AppUser appUser = findAppUserByEmail(email);
        appUser.setLocked(false);
        appUserRepository.save(appUser);

    }

    @Transactional
    public void lockAppUserByEmail(String email) {
        log.info("lockAppUserByEmail/email={}", email);
        AppUser appUser = findAppUserByEmail(email);
        appUser.setLocked(true);
        appUserRepository.save(appUser);
    }

    public Page<AppUserResponseInterface> getAllUsers(int page, int size) {
        if (page > 0 && size > 0) {
            return appUserRepository.findAllAppUsers(PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        }
        return null;
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        log.info("deleteUserByEmail/email: {}", email);
        appUserRepository.deleteByEmail(email);
    }

    @Override
    public Page<AppUser> searchUsers(String keyword, int page, int pageSize, String sortField, Sort.Direction sortDirection) {
        log.info("searchUsers/keyword = {}", keyword);
        log.info("searchUsers/page = {}", page);
        log.info("searchUsers/pageSize = {}", pageSize);
        log.info("searchUsers/sortField = {}", sortField);
        log.info("searchUsers/sortDirection = {}", sortDirection);
        Sort sort = Sort.by(sortDirection, sortField);
        return appUserRepository.searchUsers(keyword, PageRequest.of(page, pageSize, sort));
    }


    public Page<AppUser> searchAppUserByName(String keyword, int page, int size) {
        log.info("searchAppUser/keyword: {}", keyword);
        log.info("searchAppUser/page: {}", page);
        log.info("searchAppUser/color: {}", size);
        return appUserRepository.findByFirstNameOrLastNameContainingIgnoreCase(keyword, keyword, PageRequest.of(page, size));
    }

    /**
     * private methods
     */

    private AppUser findAppUserByEmail(String email) {
        log.info("findAppUserByEmail/email:{}", email);
        return appUserRepository.findByEmail(email.toLowerCase().trim()).orElseThrow(() -> new AppUserException(String.format("no user with email: %s found", email)));
    }

 private AppUser findAppUserByUsername(String username) {
        log.info("findAppUserByUsername/username:{}", username);
        return appUserRepository.findByUsername(username.toLowerCase().trim()).orElseThrow(() -> new AppUserException(String.format("no user with username: %s found", username)));
    }

    private AppUser findUserById(long userId) {
        log.info("findUserById/userId={}", userId);
        return appUserRepository.findById(userId).orElseThrow(() -> new AppUserException("user not found"));
    }

    private void revokeAllUserTokens(AppUser user) {
        log.info("revokeAllUserTokens/userEmail={}", user.getEmail());
        tokenService.revokeAllUserTokens(user);

    }


    private AppUser computeAppUserData(@NotNull RegisterUserRequest userRequest, String encodedPassword, Language language) {
        return AppUser.builder()
                .password(encodedPassword)
                .lastLogin(LocalDateTime.now())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail().toLowerCase().trim())
                .role(Role.valueOf(StringUtils.deleteWhitespace(userRequest.getRole().toUpperCase())))
                .locked(false).enabled(true)
                .firstName(StringUtils.capitalize(userRequest.getFirstName()).trim())
                .lastName(StringUtils.capitalize(userRequest.getLastName()).trim())
                .build();
    }

    private AppUser computeUpdateProfileUserData(UpdateAppUserProfileRequest request, AppUser existingUser) {
        existingUser.setUsername(request.getUsername().toLowerCase().trim());
        existingUser.setDateOfBirth(request.getDateOfBirth());
        existingUser.setGender(Gender.valueOf(request.getGender()));
        existingUser.setPhoneNumber(validatePhoneNumber(request.getPhoneNumber()));
        existingUser.setFirstName(StringUtils.capitalize(request.getFirstName()).trim());
        existingUser.setLastName(StringUtils.capitalize(request.getLastName()).trim());

        return existingUser;
    }


    private String validatePhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        if (Strings.isNotBlank(phoneNumber)) {
            if (!StringUtils.isNumeric(phoneNumber))
                throw new IllegalArgumentException("invalid phone number: number can only contain digits");
            if (phoneNumber.length() != 11) {
                //for a number 07068693731 passed as 7068693731
                if (phoneNumber.length() == 10 && phoneNumber.charAt(0) != '0') {
                    return "0".concat(phoneNumber);
                } else
                    throw new IllegalArgumentException(String.format("invalid phone number: %s, check length of number: A valid phone number should be 11 digits including the first '0' ", phoneNumber));
            } else {
                return phoneNumber;
            }

        }
        return phoneNumber;
    }


}
