package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CreditCardDto {

    @NotBlank
    private String id;
    @NotBlank
    private String numAccount;
    @NotBlank
    private String nomAccount;
    @NotBlank
    private String typeAccount;
    @NotBlank
    private Double balance;
    @NotBlank
    private Double currentBalance;
    @NotBlank
    private String status;
    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;
    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date updatedAt;
    @NotBlank
    private List<Person> listPersons;

    public CreditCardDto() {
    	listPersons = new ArrayList<>();
    }
    
    public void addListPerson(Person person) {
    	this.listPersons.add(person);
    }

}
