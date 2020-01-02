package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CreditCardDto {

    @NotEmpty
    private String id;
    @NotEmpty(message = "Campo nomBank no puede ser vacio")
    private String nomBank;
    @NotEmpty(message = "Campo numAccount no puede ser vacio")
    private String numAccount;
    @NotEmpty(message = "Campo nomAccount no puede ser vacio")
    private String nomAccount;
    @NotEmpty(message = "Campo typeAccount no puede ser vacio")
    private String typeAccount;
    @NotEmpty(message = "Campo balance no puede ser vacio")
    @Min(0)
    private Double balance;
    @NotEmpty(message = "Campo currentBalance no puede ser vacio")
    @Min(0)
    private Double currentBalance;
    @NotEmpty(message = "Campo status no puede ser vacio")
    private String status;
    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;
    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date updatedAt;
    @NotEmpty(message = "Campo cantTransactions no puede ser vacio")
    private Integer cantTransactions;
    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date datePay;
    @NotBlank
    private List<Person> listPersons;

    public CreditCardDto() {
    	listPersons = new ArrayList<>();
    }
    
    public void addListPerson(Person person) {
    	this.listPersons.add(person);
    }

}
