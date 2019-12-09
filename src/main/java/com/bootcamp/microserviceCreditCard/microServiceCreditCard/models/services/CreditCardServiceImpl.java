package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.services;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dao.ICreditCardDao;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreditCardServiceImpl implements ICreditCardService{
    @Autowired
    private ICreditCardDao dao;

    @Override
    public Flux<CreditCard> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<CreditCard> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<CreditCard> findByNumAccount(String numAccount) {
        return dao.findBynumAccount(numAccount);
    }

    @Override
    public Mono<CreditCard> saveCreditCard(CreditCard creditCard) {
        return dao.save(creditCard);
    }

    @Override
    public Mono<Void> deleteCreditCard(CreditCard creditCard) {
        return dao.delete(creditCard);
    }
}
