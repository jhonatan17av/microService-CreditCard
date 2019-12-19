package com.bootcamp.microserviceCreditCard.microServiceCreditCard.repository;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Movement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MovementRespository extends ReactiveMongoRepository<Movement, String> {

    public Flux<Movement> findBynumAccount(String numAccount);


}
