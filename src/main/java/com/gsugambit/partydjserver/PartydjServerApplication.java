package com.gsugambit.partydjserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("com.gsugambit.partydjserver")
@EnableTransactionManagement
@EnableConfigurationProperties
public class PartydjServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartydjServerApplication.class, args);
	}

}
