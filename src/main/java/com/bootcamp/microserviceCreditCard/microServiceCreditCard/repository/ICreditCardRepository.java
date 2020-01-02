package com.bootcamp.microserviceCreditCard.microServiceCreditCard.repository;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {

  Mono<CreditCard> findBynumAccount(String numAccount);

  Flux<CreditCard> findBynomBank(String nomBank);

}
