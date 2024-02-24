//package com.example.sosinventory.email;
//
//import com.example.sosinventory.utils.DateUtils;
//import com.example.sosinventory.utils.Language;
//import com.example.sosinventory.utils.ReferentialUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.MessageSource;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class UserEmailService {
//
//
//    public static final String ENABLE_ACC_PATH = "/api/v1/user/enable_acc?email=%s&token=%s";
//    public static final String FORGOT_PASSWORD_REDIRECT_PATH = "/api/v1/user/forgot_password?email=%s&token=%s";
//
//    @Value(value = "${soslifestyle.project-name}")
//    public final String PROJECT_NAME = "";
//
//    @Value(value = "${soslifestyle.env.variable-name}")
//    private final String envVariableName = "";
//
//    @Value(value = "${soslifestyle.env.prod}")
//    private final String prodEnvironment = "";
//
//    @Value("#{'${spring.mail.account-managers}'.split(',')}")
//    private final List<String> accountManagers = new ArrayList<>();
//    private final EmailService emailService;
//    private final MessageSource userMessageSource;
//    @Value(value = "${soslifestyle.base-url}")
//    private String baseUrl = "http://localhost:9998";
//
//    public String prepareWelcomeMailBody(String email, String url, List<String> textVariables, Locale locale) {
//        log.info("prepareWelcomeMailBody/email: {}", email);
//        log.info("prepareWelcomeMailBody/url: {}", url);
//        log.info("prepareWelcomeMailBody/textVariables: {}", textVariables);
//        log.info("prepareWelcomeMailBody/locale: {}", locale);
//
//        String path = String.format("%s" + File.separator + "mail-welcome.html", getFilePath());
//
//        try {
//            return buildMailBody(email, url, textVariables, locale, path);
//        } catch (IOException e) {
//            log.error("Exception when preparing email body", e);
//            throw new RuntimeException("prepareWelcomeMailBody/Cannot prepare the email body");
//        }
//    }
//
//    public void constructAndSendUserRegistrationEmailMessage(String email, String token, Language language) {
//        log.info("constructAndSendUserRegistrationEmailMessage/userEmail:{}", email);
//        log.info("constructAndSendUserRegistrationEmailMessage/token:{}", token); //todo: remove for prod
//
//
//        final String confirmationUrl = baseUrl + String.format(ENABLE_ACC_PATH, email, token);
//
//        List<String> recipients = new ArrayList<>();
//        recipients.add(email);
//        String environment = System.getenv(envVariableName);
//        if (StringUtils.equalsIgnoreCase(environment, prodEnvironment)) {
//            recipients.addAll(accountManagers); // ADD ALL IF ON PROD ENV
//        }
//        Locale locale = ReferentialUtils.getLocale(language);
//        List<String> textVariables = new ArrayList<>();
//// List<String> textVariables = Arrays.asList(UserMessageConstants.MAIL_WELCOME_01, UserMessageConstants.MAIL_WELCOME_02, UserMessageConstants.MAIL_WELCOME_03, UserMessageConstants.MAIL_WELCOME_04,
////                UserMessageConstants.MAIL_WELCOME_05, UserMessageConstants.MAIL_WELCOME_06, UserMessageConstants.MAIL_WELCOME_07);
//        String body = prepareWelcomeMailBody(email, confirmationUrl, textVariables, locale);
//
//        log.info("constructAndSendUserRegistrationEmailMessage/textVariables: {}", textVariables);
//        log.info("constructAndSendUserRegistrationEmailMessage/PROJECT_NAME: {}", PROJECT_NAME);
//        emailService.constructAndSendEmailWithTemplate(userMessageSource.getMessage("MAIL_WELCOME_SUBJECT", new String[]{PROJECT_NAME}, locale), body, recipients, language);
//    }
//
//
//    public void constructAndSendResetPasswordEmailMessage(String email, String token, Language language) {
//        log.info("constructAndSendResetPasswordEmailMessage/email: {}", email);
//        log.info("constructAndSendResetPasswordEmailMessage/token: {}", token); //todo: remove for prod
//        log.info("constructAndSendResetPasswordEmailMessage/language: {}", language);
//
//        final String confirmationUrl = baseUrl + String.format(FORGOT_PASSWORD_REDIRECT_PATH, email, token);
//
//        List<String> recipients = new ArrayList<>();
//        recipients.add(email);
//        String environment = System.getenv(envVariableName);
//        if (StringUtils.equalsIgnoreCase(environment, prodEnvironment)) {
//            recipients.addAll(accountManagers);
//        }
//        Locale locale = ReferentialUtils.getLocale(language);
//        List<String> textVariables = new ArrayList<>();
//
////        List<String> textVariables = Arrays.asList(UserMessageConstants.MAIL_RESET_01, UserMessageConstants.MAIL_RESET_02, UserMessageConstants.MAIL_RESET_03, UserMessageConstants.MAIL_RESET_04,
////                UserMessageConstants.MAIL_RESET_05, UserMessageConstants.MAIL_RESET_06);
//        String body = prepareResetPasswordMailBody(email, confirmationUrl, textVariables, locale);
//
//        emailService.constructAndSendEmailWithTemplate(userMessageSource.getMessage("MAIL_RESET_SUBJECT", new Object[]{PROJECT_NAME}, locale), body, recipients, Language.English);
//    }
//
//    public String prepareResetPasswordMailBody(String email, String url, List<String> textVariables, Locale locale) {
//        log.info("prepareResetPasswordMailBody/email: {}", email);
//        log.info("prepareResetPasswordMailBody/url: {}", url);
//        log.info("prepareResetPasswordMailBody/textVariables: {}", textVariables);
//        log.info("prepareResetPasswordMailBody/locale: {}", locale);
//        String path = String.format("%s/mail-reset.html", getFilePath());
//
//        try {
//            return buildMailBody(email, url, textVariables, locale, path);
//        } catch (IOException e) {
//            log.error("Exception when preparing email body", e);
//            throw new RuntimeException("prepareResetPasswordMailBody/Cannot prepare the email body");
//        }
//    }
//
//    private String buildMailBody(String email, String url, List<String> textVariables, Locale locale, String path) throws IOException {
//        BufferedReader bufferedReader = null;
//        InputStreamReader inputStreamReader = null;
//        try {
//            String body;
//            inputStreamReader = new InputStreamReader(prepareInputStreamFromPath(path), StandardCharsets.UTF_8);
//            bufferedReader = new BufferedReader(inputStreamReader);
//            String s;
//            StringBuilder content = new StringBuilder(1024);
//            while ((s = bufferedReader.readLine()) != null) {
//                content.append(s);
//            }
//            body = content.toString();
//            body = body.replace("{{EMAIL}}", email);
//            body = body.replace("{{LINK}}", url);
//            body = body.replace("{{CURRENT_YEAR}}", DateUtils.getCurrentYear().toString());
//            for (String textVariable : textVariables)
//                body = body.replace(prepareHtmlVariable(textVariable), userMessageSource.getMessage(textVariable, new Object[]{PROJECT_NAME}, locale));
//            return body;
//        } catch (Exception e) {
//            log.error("Exception when preparing email body", e);
//            throw new RuntimeException("buildMailBody/Cannot prepare the email body");
//        } finally {
//            closeResources(inputStreamReader, bufferedReader);
//        }
//    }
//
//
//    private String getFilePath() {
//        return "user" + File.separator + "mail";
//    }
//
//    private InputStream prepareInputStreamFromPath(String path) {
//        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
//    }
//
//    private String prepareHtmlVariable(String var) {
//        return "{{" + var + "}}";
//    }
//
//    private void closeResources(InputStreamReader inputStreamReader, BufferedReader bufferedReader) {
//        if (bufferedReader != null) {
//            try {
//                bufferedReader.close();
//            } catch (IOException e) {
//                log.error("closeResources/error:" + e);
//            }
//        }
//        if (inputStreamReader != null) {
//            try {
//                inputStreamReader.close();
//            } catch (IOException e) {
//                log.error("closeResources/error:" + e);
//
//            }
//        }
//    }
//
//}
