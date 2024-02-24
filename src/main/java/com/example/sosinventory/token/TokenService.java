package com.example.sosinventory.token;

import com.example.sosinventory.appuser.AppUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    //    @Value(value = "${ballers.token.lifetime}")
    private final long tokenLifetime = 24L;

    private static String generateSixDigitRandomNumber() {
        // It will generate 6 digit random Number.
        // from 0 to 999 999
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public void saveUserToken(AppUser user, String tokenString, TokenType tokenType) {
        Token token = Token.builder()
                .emailAddress(user.getEmail())
                .token(tokenString)
                .tokenType(tokenType)
                .build();
        tokenRepository.save(token);
    }

    @Transactional
    public Token generateAndPersistUserToken(String email, TokenType tokenType) {
        String tokenString = generateToken();
        //delete existing token
        deleteExistingTokenTypeForUser(email, tokenType);
        //save new token
        return persistUsersToken(email, tokenType, tokenString);
    }

    private Token persistUsersToken(String email, TokenType tokenType, String tokenString) {
        Token token = Token.builder()
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusYears(tokenLifetime))
                .confirmedAt(null)
                .tokenType(tokenType)
                .emailAddress(email)
                .token(tokenString)
                .build();
        Token savedToken = tokenRepository.save(token);
        log.info("persistUsersToken/token:{}", savedToken);
        return savedToken;
    }

    private void deleteExistingTokenTypeForUser(String email, TokenType tokenType) {
        tokenRepository.deleteByEmailAddressAndTokenType(email, tokenType);
    }

    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Transactional
    public Token getToken(String token) throws TokenException {
        return tokenRepository.findByToken(token).orElseThrow(() -> new TokenException("token not found"));
    }

    public Token computeConfirmedAtForToken(String token) {
        log.info("setConfirmedAt/token:{}", token);
        try {
            Token tokenValidation = getToken(token);
            tokenValidation.setConfirmedAt(LocalDateTime.now());
            return tokenRepository.save(tokenValidation);
        } catch (TokenException e) {
            throw new RuntimeException(e);
        }
    }

//    @Transactional
//    public AuthenticationResponse confirmToken(String token) throws TokenException {
//        Token tokenValidation = getToken(token);
//
//        if (tokenValidation.getConfirmedAt() != null) // confirmedAt is set to null upon creation of token, so if it has a value then it has already been used or confirmed
//            throw new TokenException("token already used");
//
//        LocalDateTime expiredAt = tokenValidation.getExpiredAt();
//
//        if (expiredAt.isBefore(LocalDateTime.now()))
//            throw new TokenException("token expired");
//
//        Token confirmedToken = computeConfirmedAtForToken(token);
//        String response = appUserService.enableAppUser(confirmedToken.getEmailAddress());
//        log.info(response);
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(confirmedToken.getEmailAddress());
//        AppUser appUser = appUserRepository.findByEmailAddress(confirmedToken.getEmailAddress()).get();
////
//        final String jwt = jwtTokenUtil.generateToken(userDetails);
//
//        return new AuthenticationResponse(jwt, appUser.getRole().getName(), appUser.getAllUserPrivileges(), appUser.getId().toString(), appUser.getFirstName(), appUser.getLastName(), appUser.getLastLogin());
////        return "Token confirmed successfully";
//    }

    public boolean existsByEmailAddressAndTokenAndTokenType(String email, String token, TokenType tokenType) {
        log.info("findByEmailAddressAndTokenAndTokenType/email: {}", email);
        log.info("findByEmailAddressAndTokenAndTokenType/token: {}", token);
        log.info("findByEmailAddressAndTokenAndTokenType/tokenType: {}", tokenType);
        return tokenRepository.existsByEmailAddressAndTokenAndTokenType(email, token, tokenType);
    }

    public String generateToken() {
        String token = generateSixDigitRandomNumber();
        log.debug("generateToken/token:" + token);
        return token;
    }

    public void revokeAllUserTokens(AppUser user) {
        List<Token> validUserTokens = tokenRepository.findAllById(user.getId());
        validUserTokens.forEach(token -> {
            token.setExpiredAt(LocalDateTime.now());
            token.setConfirmedAt(LocalDateTime.now());
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
