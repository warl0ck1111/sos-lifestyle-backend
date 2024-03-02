package com.example.sosinventory.appuser;

import com.example.sosinventory.response.ApiFailedResponse;
import com.example.sosinventory.response.ApiSuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)

public class AuthController {

    private final AppUserService service;


    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        return new ResponseEntity<>(new ApiSuccessResponse(service.registerAppUser(request)), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ApiSuccessResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = service.loginAppUser(request);
        return new ResponseEntity<>(new ApiSuccessResponse(authenticationResponse), HttpStatus.OK);
    }


    /**
     * Exception handlers
     */


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiFailedResponse> handleBadCredentialsException(BadCredentialsException e) {
        log.error("handleBadCredentialsException/ERROR: ", e);
        return new ResponseEntity<>(new ApiFailedResponse("invalid user username or password"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiFailedResponse> handleDisabledException(DisabledException e) {
        log.error("handleDisabledException/ERROR: ", e);
        return new ResponseEntity<>(new ApiFailedResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(AppUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiFailedResponse> handleAppUserException(AppUserException e) {
        log.error("handleAppUserException/");
        return new ResponseEntity<>(new ApiFailedResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiFailedResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error("handleMethodArgumentNotValidException/e :", e);
        List<String> errorList = new ArrayList<>();
        e.getAllErrors().forEach(objectError -> errorList.add(objectError.getDefaultMessage()));
        return new ResponseEntity<>(new ApiFailedResponse(errorList.get(0)), HttpStatus.BAD_REQUEST);
    }
}
