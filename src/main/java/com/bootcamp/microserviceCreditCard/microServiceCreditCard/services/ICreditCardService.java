package com.bootcamp.microserviceCreditCard.microServiceCreditCard.services;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Movement;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.CreditCardDto;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.MovPayFromAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditCardService {

    Flux<CreditCard> findAll();
    Mono<CreditCard> findById(String id);
    Mono<CreditCard> findByNumAccount(String numAccount);
    Flux<CreditCard> findByNomBank(String nomBank);
    Mono<CreditCard> saveCreditCard(CreditCard creditCard);
    Mono<CreditCardDto> saveCreditCardWithPerson(CreditCardDto creditCardDto);
    Mono<CreditCard> updateAccount(CreditCard savingAccount);
    Mono<Void> deleteCreditCard(CreditCard creditCard);

    Mono<CreditCard> saveAccountOnPerson(CreditCard creditCard, String numDoc);


    Mono<CreditCard> saveMovement(Movement movement);
    Mono<CreditCard> saveMovementFromAccount(MovPayFromAccount movPayFromAccount);
    Flux<Movement> findAllMovement();
    Flux<Movement> findMovByNumAccount(String numAccount);
}
