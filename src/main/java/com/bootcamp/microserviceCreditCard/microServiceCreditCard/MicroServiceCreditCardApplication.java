package com.bootcamp.microserviceCreditCard.microServiceCreditCard;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.repository.ICreditCardRepository;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class MicroServiceCreditCardApplication implements CommandLineRunner {

    @Autowired
    private ICreditCardRepository repo;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(MicroServiceCreditCardApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCreditCardApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        mongoTemplate.dropCollection("creditCards").subscribe();

        Flux.just(new CreditCard("Scotiabank","99966633310","Tarjeta de Credito",
                "Vip",150000.0,150000.0,
                "Active",new Date(),new Date(),0))
                .flatMap(creditCard -> repo.save(creditCard))
                .subscribe(creditCard -> log.info("Credit Card inserted :" + creditCard.getNumAccount()));


    }
}
