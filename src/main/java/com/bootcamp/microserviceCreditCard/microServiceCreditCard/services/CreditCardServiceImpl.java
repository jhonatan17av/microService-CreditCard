package com.bootcamp.microserviceCreditCard.microServiceCreditCard.services;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.configuration.Constants;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.convertion.ConvertCreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Movement;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Person;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.AccountDto;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.CreditCardDto;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.MovPayFromAccount;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.repository.ICreditCardRepository;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.repository.MovementRespository;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.services.serviceDto.IServiceClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class CreditCardServiceImpl implements ICreditCardService {

  @Autowired
  private ICreditCardRepository repoCreditCard;

  @Autowired
  private IServiceClientDto serviceClient;

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
  public Flux<CreditCard> findByNomBank(String nomBank) {
    return repoCreditCard.findBynomBank(nomBank);
  }

  @Override
  public Mono<CreditCard> saveCreditCard(CreditCard creditCard) {
    return repoCreditCard.save(creditCard);
  }

  @Override
  public Mono<CreditCardDto> saveCreditCardWithPerson(CreditCardDto creditCardDto) {

    if (creditCardDto.getNumAccount() == null || creditCardDto.getNumAccount().equalsIgnoreCase("null")) {
      creditCardDto.setNumAccount(Constants.NUM_ACCOUNT);
    }

    if (creditCardDto.getNomAccount() == null || creditCardDto.getNomAccount().equalsIgnoreCase("null")) {
      creditCardDto.setNomAccount(Constants.NOM_ACCOUNT);
    }

    return repoCreditCard.save(conv.toCreditCard(creditCardDto))
        .flatMap(creditCard -> {
          creditCardDto.getListPersons().forEach(person -> {
            person.setNomBank(creditCard.getNomBank());
            person.setNumAccount(creditCard.getNumAccount());
            person.setNomAccount(creditCard.getNomAccount());
            person.setTypeAccount(creditCard.getTypeAccount());
            person.setStatus(creditCard.getStatus());
            serviceClient.savePerson(person).block();
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
  public Mono<CreditCard> saveAccountOnPerson(CreditCard creditCard, String numDoc) {
    return serviceClient.lstAccounts(numDoc)
        .collectList()
        .flatMap(accounts -> {

          boolean value = false;

          for (AccountDto account : accounts) {
            if (account.getNomAccount().equals(creditCard.getNomAccount())
                && account.getTypeAccount().equals(creditCard.getTypeAccount())
                && account.getNomBank().equalsIgnoreCase(creditCard.getNomBank())) {
              value = true;
              break;
            }
          }

          if (creditCard.getNomAccount() == null || creditCard.getNomAccount().equalsIgnoreCase("null")) {
            creditCard.setNomAccount(Constants.NOM_ACCOUNT);
          }
          if (!value) {
            return repoCreditCard.save(creditCard)
                .flatMap(x -> {
                  return serviceClient.findBynumDoc(numDoc)
                      .flatMap(personDtoReturn -> {
                        Person p = new Person();
                        p.setNamePerson(personDtoReturn.getNamePerson());
                        p.setLastName(personDtoReturn.getLastName());
                        p.setTypeDoc(personDtoReturn.getTypeDoc());
                        p.setNumDoc(personDtoReturn.getNumDoc());
                        p.setGender(personDtoReturn.getGender());
                        p.setDateBirth(personDtoReturn.getDateBirth());
                        p.setCreatedAt(personDtoReturn.getCreatedAt());
                        p.setUpdatedAt(personDtoReturn.getUpdatedAt());
                        p.setNomBank(x.getNomBank());
                        p.setNumAccount(x.getNumAccount());
                        p.setNomAccount(x.getNomAccount());
                        p.setTypeAccount(x.getTypeAccount());
                        p.setStatus(x.getStatus());
                        return serviceClient.updatePerson(p, numDoc)
                            .flatMap(personDto1 -> {
                              personDto1.setId(creditCard.getId());
                              return Mono.just(creditCard);
                            });
                      });
                });
          } else {
            return Mono.empty();
          }
        });
  }

  @Override
  public Mono<CreditCard> saveMovement(Movement movement) {

    return repoCreditCard.findBynumAccount(movement.getNumAccount())

        .flatMap(creditCard -> {
          double comi = 0.0;

          if (movement.getTypeMovement().equalsIgnoreCase("buy") && movement.getBalanceTransaction() < creditCard.getCurrentBalance()) {

            if (creditCard.getCantTransactions() > 5) {
              movement.setCommission(movement.getBalanceTransaction() * 0.1);
            } else {
              movement.setCommission(comi);
            }

            movement.setCreatedAt(new Date());

            return repoMovement.save(movement)
                .flatMap(m -> {
                  creditCard.setCantTransactions(creditCard.getCantTransactions() + 1);
                  creditCard.setCurrentBalance(creditCard.getCurrentBalance() - movement.getBalanceTransaction() - movement.getCommission());
                  return repoCreditCard.save(creditCard);
                });

          } else if (movement.getTypeMovement().equalsIgnoreCase("pay")) {

            if (creditCard.getCantTransactions() > 5) {
              movement.setCommission(movement.getBalanceTransaction() * 0.1);
            } else {
              movement.setCommission(comi);
            }
            movement.setCreatedAt(new Date());

            return repoMovement.save(movement).
                flatMap(m -> {
                  creditCard.setCantTransactions(creditCard.getCantTransactions() + 1);
                  creditCard.setCurrentBalance(creditCard.getCurrentBalance() +
                      movement.getBalanceTransaction() - movement.getCommission());
                  return repoCreditCard.save(creditCard);
                });
          }
          return Mono.just(creditCard);
        });
  }

  @Override
  public Mono<CreditCard> saveMovementFromAccount(MovPayFromAccount movPayFromAccount) {
    return repoCreditCard.findBynumAccount(movPayFromAccount.getNumCreditCard())
        .flatMap(creditCard -> {
          double comi = 0.0;

          if (movPayFromAccount.getTypeMovement().equalsIgnoreCase("buy") &&
              creditCard.getCurrentBalance() > movPayFromAccount.getBalanceTransaction()) {
            if (creditCard.getCantTransactions() > 5) {
              movPayFromAccount.setCommission(movPayFromAccount.getBalanceTransaction() * 0.1);
            } else {
              movPayFromAccount.setCommission(comi);
            }

            movPayFromAccount.setCreatedAt(new Date());

            return repoMovement.save(conv.toMovement(movPayFromAccount))
                .flatMap(m -> {
                  creditCard.setCantTransactions(creditCard.getCantTransactions() + 1);
                  creditCard.setCurrentBalance(creditCard.getCurrentBalance() - m.getBalanceTransaction() - m.getCommission());
                  return repoCreditCard.save(creditCard);
                });
          } else if (movPayFromAccount.getTypeMovement().equalsIgnoreCase("pay")) {

            if (creditCard.getCantTransactions() > 5) {
              movPayFromAccount.setCommission(movPayFromAccount.getBalanceTransaction() * 0.1);
            } else {
              movPayFromAccount.setCommission(comi);
            }
            movPayFromAccount.setCreatedAt(new Date());

            return repoMovement.save(conv.toMovement(movPayFromAccount))
                .flatMap(movement -> {
                  movement.setNumAccount(movPayFromAccount.getNumAccount());
                  movement.setTypeMovement("retiro");
                  movement.setBalanceTransaction(movPayFromAccount.getBalanceTransaction());
                  movement.setCommission(movPayFromAccount.getCommission());
                  movement.setCreatedAt(new Date());
                  if (movPayFromAccount.getNomAccount().equalsIgnoreCase("Cuenta de Ahorro")) {
                    return serviceClient.saveMovementSavingA(movement);
                  } else if (movPayFromAccount.getNomAccount().equalsIgnoreCase("Cuenta Corriente")) {
                    return serviceClient.saveMovementCurrentA(movement);
                  }

                  return Mono.empty();

                }).flatMap(movement -> {
                  creditCard.setCantTransactions(creditCard.getCantTransactions() + 1);
                  creditCard.setCurrentBalance(creditCard.getCurrentBalance() + movPayFromAccount.getBalanceTransaction() - movPayFromAccount.getCommission());
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
