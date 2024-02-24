package com.example.sosinventory.appuser;

import com.example.sosinventory.response.ApiErrorResponse;
import com.example.sosinventory.response.ApiFailedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Okala Bashir .O.
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppUserController {


    private final AppUserService appUserService;


    @PutMapping("/users/enable_acc")
    public ResponseEntity<Void> activateAppUserAccount(@RequestParam String email, @RequestParam String token) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users/forgot_password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        appUserService.forgotPassword(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/users/reset_password")
    public ResponseEntity<Void> resetAppUserPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        appUserService.resetAppUserPassword(resetPasswordRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


//    @PutMapping("kyc_verification/{idNumber}")
//    public ResponseEntity<ApiSuccessResponse> verifyAppUser(@RequestParam String idNumber,@RequestParam VerificationType verificationType, @RequestBody AppUserVerificationRequest verificationRequest) {
//        return new ResponseEntity<>(new ApiSuccessResponse(appUserService.verifyAppUsersKyc(idNumber, verificationType, verificationRequest)), HttpStatus.OK);
//    }


    @PreAuthorize("hasAnyRole()")
    @GetMapping("/users/{userId}/profile")
    public ResponseEntity<GetAppUserProfileResponse> profile(@PathVariable long userId) {
        GetAppUserProfileResponse userProfile = appUserService.getAppUserProfile(userId);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/update_profile")
    public ResponseEntity<Void> updateProfile(@PathVariable String userId, @RequestBody UpdateAppUserProfileRequest request) {
        appUserService.updateAppUserProfile(userId, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<Page<AppUser>> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                          @RequestParam(name = "sortField", defaultValue = "creationDate") String sortField,
                                                          @RequestParam(name = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection) {
        Page<AppUser> allUsers = appUserService.findAllUsers(page, pageSize, sortField, sortDirection);
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{email}/enable_acc")
    public ResponseEntity<Void> enableAppUser(@PathVariable String email) {
        appUserService.enableAppUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{email}/disable_acc")
    public ResponseEntity<Void> disableAppUser(@PathVariable String email) {
        appUserService.disableAppUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{email}/lock_acc")
    public ResponseEntity<Void> lockAppUserAccount(@PathVariable String email) {
        appUserService.lockAppUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{email}/unlock_acc")
    public ResponseEntity<Void> unlockAppUserAccount(@PathVariable String email) {
        appUserService.unlockAppUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search/users")
    public ResponseEntity<Page<AppUser>> searchUsers(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sortField", defaultValue = "creationDate") String sortField,
            @RequestParam(name = "sortDirection", defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        return ResponseEntity.ok(appUserService.searchUsers(keyword, page, pageSize, sortField, sortDirection));
    }



//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        service.refreshToken(request, response);
//    }

    /**
     * Exceptions Handlers
     */


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiFailedResponse> handleUserException(UserException e) {
        log.info("handleUserException/");
        log.info("handleUserException/e=" + e);
        return new ResponseEntity<>(new ApiFailedResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        log.info("handleException/");
        log.error("handleException/e=" + e);
        return new ResponseEntity<>(new ApiErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiFailedResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.info("handleAccessDeniedException/");
        log.error("handleAccessDeniedException/e=" + e);
        return new ResponseEntity<>(new ApiFailedResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiFailedResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error("handleMethodArgumentNotValidException/e :", e);
        List<String> errorList = new ArrayList<>();
        e.getAllErrors().forEach(objectError -> errorList.add(objectError.getDefaultMessage()));
        return new ResponseEntity<>(new ApiFailedResponse(errorList), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AppUserException.class)
    public ResponseEntity<ApiErrorResponse> handleAppUserException(AppUserException e) {
        log.info("handleAppUserException/");
        return new ResponseEntity<>(new ApiErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
