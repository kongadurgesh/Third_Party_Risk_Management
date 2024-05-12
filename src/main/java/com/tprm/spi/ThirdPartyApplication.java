package com.tprm.spi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.tprm" })
@EnableTransactionManagement
@EnableFeignClients
public class ThirdPartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirdPartyApplication.class, args);
	}

}
