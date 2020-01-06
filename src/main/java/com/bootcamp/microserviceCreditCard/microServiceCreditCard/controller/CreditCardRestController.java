package com.bootcamp.microserviceCreditCard.microServiceCreditCard.controller;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Movement;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.CreditCardDto;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.MovPayFromAccount;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.services.ICreditCardService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

@RestController
@Api(value = "Controller-Person", description = "Methods on Controller to Person")
@RequestMapping("/creditCard")
public class CreditCardRestController {

  @Autowired
  private ICreditCardService creditCardService;

  @GetMapping
  public Mono<ResponseEntity<Flux<CreditCard>>> findAllCreditCard() {
    return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(creditCardService.findAll()))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<CreditCard>> findByID(@PathVariable String id) {
    return creditCardService.findById(id)
        .map(creditCard -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(creditCard))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/numAccount/{numAccount}")
  public Mono<ResponseEntity<CreditCard>> findByNumAccount(@PathVariable String numAccount) {
    return creditCardService.findByNumAccount(numAccount)
        .map(creditCard -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(creditCard))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<CreditCardDto>> saveCreditCardWithPerson(@RequestBody CreditCardDto creditCardDto) {

    return Mono.just(creditCardDto)
        .flatMap(creditCardDtoMono -> {
          return creditCardService.saveCreditCardWithPerson(creditCardDto)
              .map(s -> ResponseEntity.created(URI.create("/creditCard"))
                  .contentType(MediaType.APPLICATION_JSON).body(s));
        });

  }

  @PostMapping("/account")
  public Mono<ResponseEntity<CreditCard>> saveCreditCard(@RequestBody CreditCard creditCard) {

    return Mono.just(creditCard)
        .flatMap(creditCardMono -> {
          return creditCardService.saveCreditCard(creditCardMono)
              .map(s -> ResponseEntity.created(URI.create("/creditCard"))
                  .contentType(MediaType.APPLICATION_JSON).body(s));
        });

  }

  @PostMapping("/onPerson/{numDoc}")
  public Mono<ResponseEntity<CreditCard>> saveAccountOnPerson(@RequestBody CreditCard creditCardMono, @PathVariable String numDoc) {
    return Mono.just(creditCardMono)
        .flatMap(savingAccountMono1 -> {
          return creditCardService.saveAccountOnPerson(creditCardMono, numDoc)
              .map(s -> ResponseEntity.created(URI.create("/creditCard"))
                  .contentType(MediaType.APPLICATION_JSON).body(s));
        });
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<CreditCard>> updateCreditCard(@RequestBody CreditCard creditCard, @PathVariable String id) {
    return creditCardService.findById(id)
        .flatMap(p -> {
          p.setNumAccount(creditCard.getNumAccount());
          p.setNomAccount(creditCard.getNomAccount());
          p.setBalance(creditCard.getBalance());
          p.setCurrentBalance(creditCard.getCurrentBalance());
          p.setStatus(creditCard.getStatus());
          p.setUpdatedAt(new Date());
          return creditCardService.saveCreditCard(p);
        }).map(per -> ResponseEntity
            .created(URI.create("/creditCard".concat(per.getId())))
            .contentType(MediaType.APPLICATION_JSON)
            .body(per))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteCreditCard(@PathVariable String id) {
    return creditCardService.findById(id)
        .flatMap(creditCard -> {
          return creditCardService.deleteCreditCard(creditCard)
              .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/saveMov")
  public Mono<ResponseEntity<CreditCard>> saveMovement(@RequestBody Movement movement) {
    return creditCardService.saveMovement(movement)
        .map(savingAccount -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(savingAccount))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/saveMovFromAccount")
  public Mono<ResponseEntity<CreditCard>> saveMovementFromAccount(@RequestBody MovPayFromAccount movement) {
    return creditCardService.saveMovementFromAccount(movement)
        .map(savingAccount -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(savingAccount))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/movements")
  public Mono<ResponseEntity<Flux<Movement>>> findAllMovement() {
    return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(creditCardService.findAllMovement()))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/movements/{numAccount}")
  public Mono<ResponseEntity<Flux<Movement>>> findMovByNumAccount(@PathVariable String numAccount) {
    return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(creditCardService.findMovByNumAccount(numAccount)))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
