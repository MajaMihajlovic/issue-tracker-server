package com.issuetracker.issuetracker.util;

import com.issuetracker.issuetracker.common.exception.BadRequestException;
import org.springframework.scheduling.annotation.Async;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class EmailSender {
    private static final String SENDER_MAIL;
    private static final String PASSWORD;

    static {
        ResourceBundle propertyResourceBundle = PropertyResourceBundle.getBundle("mail");
        SENDER_MAIL = propertyResourceBundle.getString("SENDER_MAIL");
        PASSWORD = propertyResourceBundle.getString("PASSWORD");
    }

    private static Properties getTLSSetProperty() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        return properties;
    }

    public static void sendNotification(String text, String projectName) {
        String mail="";
        try {
             mail = text.split(" ")[2].replace("[", "").replace("]", "");
        }catch(Exception e){
            mail=text;
        }
        System.out.println(mail);
        try {
            notify(mail,"You are added as participant in project "+projectName+"");
        } catch (BadRequestException e) {
            e.printStackTrace();
        }
    }

    @Async
    public static void notify(String recipientMail, String messageText) throws BadRequestException {
        Properties properties = getTLSSetProperty();
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_MAIL, PASSWORD);
            }
        });
        try {
            Message message = getMimeMessage(session,recipientMail,"Notification",messageText);
            Transport.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BadRequestException("Recipient mail not found.");
        }
    }


    public static void sendRegistrationLink(String recipientMail, String registrationToken) throws BadRequestException {
        Properties properties = getTLSSetProperty();

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_MAIL, PASSWORD);
            }
        });

        try {
            Message message = getMimeMessage(session,recipientMail,"Registration","You can register on this link http://localhost:8020 by clicking on button Register. Your token is \" + registrationToken + \".\"");
            message.setFrom(new InternetAddress(SENDER_MAIL, "Issue Tracker"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientMail));
            message.setSubject("Registration");
            message.setText("You can register on this link http://localhost:8020 by clicking on button Register. Your token is " + registrationToken + ".");

            Transport.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BadRequestException("Recipient mail not found.");
        }
    }

    public static void sendNewPassword(String recipientMail, String newPassword) throws BadRequestException {
        Properties properties = getTLSSetProperty();

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_MAIL, PASSWORD);
            }
        });

        try {
            Message message=getMimeMessage(session,recipientMail,"Registration",
                    "Your new password is " + newPassword + ". Please change it immediately after login.");
            Transport.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BadRequestException("Recipient mail not found.");
        }
    }

    private static Message getMimeMessage(Session session,String recipientMail, String subject,String text) throws UnsupportedEncodingException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_MAIL, "Issue Tracker"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientMail));
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
