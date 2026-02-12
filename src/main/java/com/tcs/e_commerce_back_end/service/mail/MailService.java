package com.tcs.e_commerce_back_end.service.mail;

import com.tcs.e_commerce_back_end.config.mailSender.MailConfigProperties;
import com.tcs.e_commerce_back_end.model.dto.order.DtoMailMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
  private final JavaMailSender javaMailSender;
  private final MailConfigProperties properties;

  public void sendMail(DtoMailMessage message) {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setFrom(properties.getMailHost());
    mail.setTo(message.getEmail());
    mail.setSubject(message.getSubject());
    mail.setText(message.getMessage());
    javaMailSender.send(mail);
  }
}
