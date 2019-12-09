package com.bootcamp.microserviceCreditCard.microServiceCreditCard;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dao.ICreditCardDao;
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
    private ICreditCardDao dao;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(MicroServiceCreditCardApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceCreditCardApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mongoTemplate.dropCollection("persons").subscribe();

        Flux.just(new CreditCard("8888888888","Credit Card",84000.00,1000.00,"Active",new Date(),new Date()))
                .flatMap(creditCard -> dao.save(creditCard))
                .subscribe(creditCard -> log.info("Person inserted :" + creditCard.getNumAccount()));


    }
}
