package com.tcs.e_commerce_back_end;

import com.tcs.e_commerce_back_end.config.admin.SystemAdminProperty;
import com.tcs.e_commerce_back_end.config.fileSystem.FileSystemProperties;
import com.tcs.e_commerce_back_end.config.jwt.RSAProperties;
import com.tcs.e_commerce_back_end.config.mailSender.MailConfigProperties;
import com.tcs.e_commerce_back_end.config.stripe.StripProperties;
import com.tcs.e_commerce_back_end.config.websocket.WebSocketProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableMethodSecurity
@EnableConfigurationProperties({
		MailConfigProperties.class,
		SystemAdminProperty.class,
		WebSocketProperties.class,
		FileSystemProperties.class,
		StripProperties.class,
		RSAProperties.class
})
public class ECommerceBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceBackEndApplication.class, args);
	}

}
