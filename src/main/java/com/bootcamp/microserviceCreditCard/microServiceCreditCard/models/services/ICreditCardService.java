package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.services;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditCardService {

    public Flux<CreditCard> findAll();

    public Mono<CreditCard> findById(String id);

    public Mono<CreditCard> findByNumAccount(String numAccount);

    public Mono<CreditCard> saveCreditCard(CreditCard creditCard);

    public Mono<Void> deleteCreditCard(CreditCard creditCard);

}
