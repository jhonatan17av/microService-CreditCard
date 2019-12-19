package com.bootcamp.microserviceCreditCard.microServiceCreditCard.repository;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ICreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {

    public Mono<CreditCard> findBynumAccount(String numAccount);

}
