//package com.example.sosinventory.email;
//
//import com.example.sosinventory.utils.Language;
//import jakarta.activation.DataHandler;
//import jakarta.mail.Multipart;
//import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMessage;
//import jakarta.mail.internet.MimeMultipart;
//import jakarta.mail.util.ByteArrayDataSource;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.logging.log4j.util.Strings;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.InputStreamSource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import java.io.InputStream;
//import java.util.*;
//
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class EmailService {
//
//    @Value(value = "${soslifestyle.project-name}")
//    public final String PROJECT_NAME = "";
//    private final JavaMailSender emailSender;
//    @Value(value = "${soslifestyle.env.variable-name}")
//    private final String envVariableName = "";
//    @Value(value = "${soslifestyle.env.prod}")
//    private final String prodEnvironment = "";
//    @Value("${spring.mail.from}")
//    private String from;
//    @Value("${spring.mail.cc}")
//    private String cc;
//    @Value("#{'${spring.mail.bcc}'.split(',')}")
//    private List<String> bcc;
//
//    public void constructAndSendEmail(String subject, String body, List<String> emailRecipients) {
//        constructAndSendEmail(subject, body, emailRecipients, null, false, true);
//    }
//
//
//    public void constructAndSendEmail(String subject, String body, List<String> emailRecipients, Map<String, byte[]> attachments, boolean isHtmlContent, boolean addBcc) {
//
//        try {
//
//            if (Strings.isBlank(subject)) {
//                subject = "Baller";
//            }
//
//            String environment = System.getenv(envVariableName);
//            if (StringUtils.equalsIgnoreCase(environment, prodEnvironment) == Boolean.FALSE) {
//                subject = "No-Reply" + "-" + subject;
//            }
//
//            if (body == null) {
//                body = " ";
//            }
//
//            //update recipients list : need a new reference since some list properties can be passed
//            List<String> emailRecipientsWithSupport = new ArrayList<>();
//            if (emailRecipients != null && !emailRecipients.isEmpty()) {
//                emailRecipientsWithSupport.addAll(emailRecipients);
//            }
//            //add support
//            if (Strings.isNotBlank(cc))
//                emailRecipientsWithSupport.add(cc);
//
//            //send email
//            MimeMessage email = emailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(email, true, "UTF-8");
//
//            helper.setFrom(from);
//            helper.setTo(emailRecipientsWithSupport.toArray(new String[0]));
//            helper.setSubject(subject);
//            if (addBcc == Boolean.TRUE) {
//                helper.setBcc(bcc.toArray(new String[0]));
//            } else {
//                helper.setBcc(new String[0]);
//            }
//            if (isHtmlContent) {
//                helper.setText(body, true);
//            } else {
//                helper.setText(body);
//            }
//
//            if (attachments != null) {
//                for (Map.Entry<String, byte[]> attachment : attachments.entrySet()) {
//                    InputStreamSource attachmentDoc = new ByteArrayResource(attachment.getValue());
//                    helper.addAttachment(attachment.getKey(), attachmentDoc);
//                }
//            }
//
//            emailSender.send(email);
//
//        } catch (Exception e) {
//
//            log.info("constructAndSendEmail/EXCEPTION", e);
//            log.info("constructAndSendEmail/subject" + subject);
//            log.info("constructAndSendEmail/body" + body);
//            log.info("constructAndSendEmail/emailRecipients" + emailRecipients);
//
//            sendRecoverEmail(subject, emailRecipients, body);
//        }
//
//    }
//
//
//    private void sendRecoverEmail(String subject, List<String> emailRecipients, String body) {
//
//        log.info("sendRecoverEmail/subject" + subject);
//        log.info("sendRecoverEmail/emailRecipients" + emailRecipients);
//        log.info("sendRecoverEmail/body" + body);
//
//        try {
//
//            //send email
//            SimpleMailMessage email = new SimpleMailMessage();
//            email.setFrom(from);
//            List<String> recipients = new ArrayList<>();
//            recipients.add(cc);
//            email.setTo(recipients.toArray(new String[0]));
//            email.setBcc(bcc.toArray(new String[0]));
//            email.setSubject("BALLER ALERT: UNABLE TO SEND EMAIL - " + subject);
//            email.setText("You have to DEAL with this email since recipients have not received it" + "\n" + "Initial emailRecipients = " + emailRecipients + "\n" + "Initial body = " + body);
//
//            emailSender.send(email);
//
//        } catch (Exception e) {
//
//            log.info("sendRecoverEmail/EXCEPTION" + e);
//            log.info("sendRecoverEmail/subject" + subject);
//            log.info("sendRecoverEmail/body" + body);
//            log.info("sendRecoverEmail/emailRecipients" + emailRecipients);
//            e.printStackTrace();
//
//        }
//    }
//
//
//    public void constructAndSendEmailWithTemplate(String subject, String htmlBody, List<String> emailRecipients, Language language) {
//        constructAndSendEmailWithTemplate(subject, htmlBody, emailRecipients, null, null, true, language);
//    }
//
//    public void constructAndSendEmailWithTemplate(String subject, String htmlBody, List<String> emailRecipients, Map<String, byte[]> attachments, Map<String, InputStream> mapInlineImages, boolean addBcc, Language language) {
//        log.info("constructAndSendEmailWithTemplate/subject: {}", subject);
//        log.info("constructAndSendEmailWithTemplate/htmlBody length: {}", htmlBody.length());
//        log.info("constructAndSendEmailWithTemplate/emailRecipients: {}", emailRecipients);
//        log.info("constructAndSendEmailWithTemplate/attachments: {}", attachments != null ? attachments.color() : null);
//        log.info("constructAndSendEmailWithTemplate/mapInlineImages: {}", mapInlineImages != null ? mapInlineImages.color() : null);
//        log.info("constructAndSendEmailWithTemplate/addBcc: {}", addBcc);
//        log.info("constructAndSendEmailWithTemplate/language: {}", language);
//        try {
//            if (mapInlineImages == null) {
//                mapInlineImages = prepareMailImages(language);
//            } else {
//                mapInlineImages.putAll(prepareMailImages(language));
//            }
//            if (subject == null) {
//                subject = PROJECT_NAME;
//            }
//            String environment = System.getenv(envVariableName);
//            log.info("constructAndSendEmailWithTemplate/environment: {}", environment);
//            if (StringUtils.equalsIgnoreCase(environment, prodEnvironment) == Boolean.FALSE) {
//                subject = environment + "-" + subject;
//            }
//
//            //update recipients list : need a new reference since some static list properties can be passed and then then would get updated
//            List<String> emailRecipientsWithSupport = new ArrayList<>();
//            if (emailRecipients != null && !emailRecipients.isEmpty()) {
//                emailRecipientsWithSupport.addAll(emailRecipients);
//            }
//            //add support
//            emailRecipientsWithSupport.add(cc);
//
//            //send email
//            MimeMessage email = emailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(email, true, "UTF-8");
//
//            helper.setFrom(from);
//            helper.setTo(emailRecipientsWithSupport.toArray(new String[0]));
//            helper.setSubject(subject);
//            if (addBcc == Boolean.TRUE) {
//                helper.setBcc(bcc.toArray(new String[0]));
//            } else {
//                helper.setBcc(new String[0]);
//            }
//
//
//            // creates message part
//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setContent(htmlBody, "text/html; charset=UTF-8");
//
//            // creates multi-part
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart);
//
//            // adds inline image attachments
//            if (mapInlineImages != null && !mapInlineImages.isEmpty()) {
//                Set<String> setImageID = mapInlineImages.keySet();
//
//                for (String contentId : setImageID) {
//                    MimeBodyPart imagePart = new MimeBodyPart();
//                    imagePart.setHeader("Content-ID", "<" + contentId + ">");
//                    imagePart.setDisposition(MimeBodyPart.INLINE);
//                    InputStream imageInputStream = mapInlineImages.get(contentId);
//                    if (imageInputStream != null) {
//                        ByteArrayDataSource dataSource = new ByteArrayDataSource(imageInputStream, "image/x-png");
//                        DataHandler dataHandler = new DataHandler(dataSource);
//                        imagePart.setDataHandler(dataHandler);
//                        multipart.addBodyPart(imagePart);
//                    }
//                }
//            }
//            if (attachments != null) {
//                for (Map.Entry<String, byte[]> attachment : attachments.entrySet()) {
//                    InputStreamSource attachmentDoc = new ByteArrayResource(attachment.getValue());
//                    helper.addAttachment(attachment.getKey(), attachmentDoc);
//                }
//            }
//            email.setContent(multipart, "text/html; charset=UTF-8");
//            emailSender.send(email);
//
//        } catch (Exception e) {
//
//            log.info("constructAndSendEmail/EXCEPTION" + e);
//            log.info("constructAndSendEmail/subject" + subject);
//            log.info("constructAndSendEmail/body" + htmlBody);
//            log.info("constructAndSendEmail/emailRecipients" + emailRecipients);
//
//            sendRecoverEmail(subject, emailRecipients, htmlBody);
//        }
//    }
//
//
//    public Map<String, InputStream> prepareMailImages(Language language) {
//        log.info("prepareMailImages/language: {}", language);
//        if (Language.English.equals(language)) {
//            Map<String, InputStream> mapInlineImages = new HashMap<>();
//            String mailPath = "mail/";
//            mapInlineImages.put("logo-soslifestyle", Thread.currentThread().getContextClassLoader().getResourceAsStream(mailPath + "/soslifestyle-logo.png"));
//            return mapInlineImages;
//        }
//
//        log.info("prepareMailImages/email for the language {} is not supported", language);
//        return null;
//    }
//
//}
