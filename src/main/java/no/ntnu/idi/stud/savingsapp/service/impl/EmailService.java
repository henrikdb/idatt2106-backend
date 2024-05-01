package no.ntnu.idi.stud.savingsapp.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Service class for handling email operations.
 */
@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  /**
   * Sends a password reset email to the specified email address.
   * The email includes a unique password reset token embedded within the email content.
   *
   * @param email The email address to which the password reset email is sent.
   * @param token The password reset token to be included in the email.
   * @throws MessagingException if any error occurs while creating or sending the email.
   * @throws IOException if there is an error reading the email template file.
   */
  public void sendForgotPasswordEmail(String email, String token) throws MessagingException, IOException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setFrom("noreply@sparesti.net", "Sparesti");
    helper.setTo(email);
    helper.setSubject("Sparesti - Password Reset");

    ClassPathResource htmlResource = new ClassPathResource("reset-password/reset-password.html");
    String html =  StreamUtils.copyToString(htmlResource.getInputStream(), StandardCharsets.UTF_8);
    html = html.replace("{RESET_TOKEN}", token);
    helper.setText(html, true);

    helper.addInline("logo", new ClassPathResource("reset-password/assets/logo.png"));

    mailSender.send(message);
  }
}
