package com.bootcamp.microserviceCreditCard.microServiceCreditCard.services;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.convertion.ConvertCreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Movement;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.CreditCardDto;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.repository.ICreditCardRepository;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.repository.MovementRespository;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.services.serviceDto.IPersonServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class CreditCardServiceImpl implements ICreditCardService{

    @Autowired
    private ICreditCardRepository repoCreditCard;

    @Autowired
    private IPersonServiceDto personService;

    @Autowired
    private MovementRespository repoMovement;

    @Autowired
    private ConvertCreditCard conv;

    @Override
    public Flux<CreditCard> findAll() {
        return repoCreditCard.findAll();
    }

    @Override
    public Mono<CreditCard> findById(String id) {
        return repoCreditCard.findById(id);
    }

    @Override
    public Mono<CreditCard> findByNumAccount(String numAccount) {
        return repoCreditCard.findBynumAccount(numAccount);
    }

    @Override
    public Mono<CreditCard> saveCreditCard(CreditCard creditCard) {
        return repoCreditCard.save(creditCard);
    }

    @Override
    public Mono<CreditCardDto> saveCreditCardWithPerson(CreditCardDto creditCardDto) {
        return repoCreditCard.save(conv.toCreditCard(creditCardDto))
                .flatMap(creditCard -> {
                    creditCardDto.getListPersons().forEach(person -> {
                        person.setNumAccount(creditCard.getNumAccount());
                        person.setNomAccount(creditCard.getNomAccount());
                        person.setTypeAccount(creditCard.getTypeAccount());
                        person.setStatus(creditCard.getStatus());
                        personService.savePerson(person).block();
                    });
                    return Mono.just(creditCardDto);
                });
    }

    @Override
    public Mono<CreditCard> updateAccount(CreditCard savingAccount) {
        return repoCreditCard.save(savingAccount);
    }

    @Override
    public Mono<Void> deleteCreditCard(CreditCard creditCard) {
        return repoCreditCard.delete(creditCard);
    }

    @Override
    public Mono<CreditCard> saveMovement(Movement movement) {

        return repoCreditCard.findBynumAccount(movement.getNumAccount())

                .flatMap(creditCard -> {
                    double comi = 0.0;

                    if (movement.getTypeMovement().equalsIgnoreCase("retiro") && movement.getBalanceTransaction() < creditCard.getCurrentBalance()){

                        if (creditCard.getCantTransactions() > 5){
                            movement.setCommission(movement.getBalanceTransaction() * 0.1);
                        }else {
                            movement.setCommission(comi);
                        }

                        movement.setCreatedAt(new Date());

                        return repoMovement.save(movement)
                                .flatMap(m -> {
                                    creditCard.setCantTransactions(creditCard.getCantTransactions() + 1);
                                    //movement.setCommission(movement.getBalanceTransaction() * 0.1);
                                    creditCard.setCurrentBalance(creditCard.getCurrentBalance() - movement.getBalanceTransaction() - movement.getCommission());
                                    return repoCreditCard.save(creditCard);
                                });

                    }else if (movement.getTypeMovement().equalsIgnoreCase("deposito") && movement.getBalanceTransaction() < creditCard.getCurrentBalance()){
                        if (creditCard.getCantTransactions() > 5){
                            movement.setCommission(movement.getBalanceTransaction() * 0.1);
                        }else {
                            movement.setCommission(comi);
                        }
                        movement.setCreatedAt(new Date());

                        return repoMovement.save(movement).
                                flatMap(m ->{
                                    creditCard.setCantTransactions(creditCard.getCantTransactions() + 1);
                                    creditCard.setCurrentBalance(creditCard.getCurrentBalance() + movement.getBalanceTransaction() - movement.getCommission());
                                    return repoCreditCard.save(creditCard);
                                });
                    }
                    return Mono.just(creditCard);
                });
    }

    @Override
    public Flux<Movement> findAllMovement() {
        return repoMovement.findAll();
    }

    @Override
    public Flux<Movement> findMovByNumAccount(String numAccount) {
        return repoMovement.findBynumAccount(numAccount);
    }

}
