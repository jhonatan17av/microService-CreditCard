package com.bootcamp.microserviceCreditCard.microServiceCreditCard.controller;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Movement;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.CreditCardDto;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.services.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
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
    public Mono<ResponseEntity<Map<String, Object>>> saveCreditCompanyWithPerson(@RequestBody Mono<CreditCardDto> creditCardDtoMono) {

        Map<String, Object> respuesta = new HashMap<>();

        return creditCardDtoMono.flatMap(creditCardDto -> {
            return creditCardService.saveCreditCardWithPerson(creditCardDto)
                    .map(p -> {
                        respuesta.put("creditCard :", creditCardDto);
                        return ResponseEntity
                                .created(URI.create("/creditCard"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(respuesta);
                    });
        }).onErrorResume(throwable -> {
            return Mono.just(throwable).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo" + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("Errors : ", list);
                        respuesta.put("timestamp : ", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
        });
    }

    @PostMapping("/account")
    public Mono<ResponseEntity<Map<String, Object>>> saveCreditCompany(@RequestBody Mono<CreditCard> creditCardMono) {

        Map<String, Object> respuesta = new HashMap<>();

        return creditCardMono.flatMap(creditCard -> {
            if (creditCard.getCreatedAt() == null) {
                creditCard.setCreatedAt(new Date());
            }
            return creditCardService.saveCreditCard(creditCard)
                    .map(p -> {
                        respuesta.put("creditCard :", creditCard);
                        return ResponseEntity
                                .created(URI.create("/creditCard"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(respuesta);
                    });
        }).onErrorResume(throwable -> {
            return Mono.just(throwable).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo" + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("Errors : ", list);
                        respuesta.put("timestamp : ", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
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
    public Mono<ResponseEntity<CreditCard>> movimiento2(@RequestBody Movement movement) {
        return creditCardService.saveMovement(movement)
                .map(savingAccount -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(savingAccount))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/movements")
    public Mono<ResponseEntity<Flux<Movement>>> findAllMovement(){
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(creditCardService.findAllMovement()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/movements/{numAccount}")
    public Mono<ResponseEntity<Flux<Movement>>> findMovByNumAccount(@PathVariable String numAccount){
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(creditCardService.findMovByNumAccount(numAccount)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
