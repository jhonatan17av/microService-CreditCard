package com.bootcamp.microserviceCreditCard.microServiceCreditCard.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServiceConfig {

  @Value("${config.base.endpoint}")
  private String pathPerson;
  @Value("${config.base.endpoint2}")
  private String pathSavingAccount;

  @Bean
	@Qualifier("personService")
  public WebClient webClientPerson() {
    return WebClient.create(pathPerson);
  }

	@Bean
	@Qualifier("savingAccount")
	public WebClient webClientSavingAccount() {
		return WebClient.create(pathSavingAccount);
	}
}
