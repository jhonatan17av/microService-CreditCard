package com.bootcamp.microserviceCreditCard.microServiceCreditCard.services.serviceDto;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Person;
import reactor.core.publisher.Mono;

public interface IPersonServiceDto {

	public Mono<Person> savePerson(Person person);
	
}
