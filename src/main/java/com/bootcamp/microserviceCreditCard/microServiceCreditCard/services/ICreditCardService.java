package com.bootcamp.microserviceCreditCard.microServiceCreditCard.services;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Movement;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.CreditCardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditCardService {

    Flux<CreditCard> findAll();
    Mono<CreditCard> findById(String id);
    Mono<CreditCard> findByNumAccount(String numAccount);
    Mono<CreditCard> saveCreditCard(CreditCard creditCard);
    Mono<CreditCardDto> saveCreditCardWithPerson(CreditCardDto creditCardDto);
    Mono<CreditCard> updateAccount(CreditCard savingAccount);
    Mono<Void> deleteCreditCard(CreditCard creditCard);

    Mono<CreditCard> saveMovement(Movement movement);
    Flux<Movement> findAllMovement();
    Flux<Movement> findMovByNumAccount(String numAccount);
}
