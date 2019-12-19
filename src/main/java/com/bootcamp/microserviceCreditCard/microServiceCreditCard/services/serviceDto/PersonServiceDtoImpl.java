package com.bootcamp.microserviceCreditCard.microServiceCreditCard.services.serviceDto;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceDtoImpl implements IPersonServiceDto{

	@Autowired
	private WebClient client;
	
	@Override
	public Mono<Person> savePerson(Person person) {
		return client.post()
				.accept(MediaType.APPLICATION_JSON)
				.syncBody(person)
				.retrieve()
				.bodyToMono(Person.class);
	}
}
