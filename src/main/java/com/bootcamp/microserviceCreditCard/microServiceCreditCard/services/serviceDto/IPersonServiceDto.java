package com.bootcamp.microserviceCreditCard.microServiceCreditCard.services.serviceDto;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Person;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.AccountDto;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.PersonDtoReturn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonServiceDto {

	Mono<Person> savePerson(Person person);
	Mono<PersonDtoReturn> findBynumDoc(String numDoc);
	Mono<PersonDtoReturn> updatePerson(Person person, String numDoc);
	Flux<AccountDto> lstAccounts(String numDoc);
}
