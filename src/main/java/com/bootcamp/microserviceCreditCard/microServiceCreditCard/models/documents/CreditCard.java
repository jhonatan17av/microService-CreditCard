package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Document(collection = "creditCards")
public class CreditCard {

    @Id
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

    public CreditCard() {
    }

    public CreditCard(@NotBlank String numAccount, @NotBlank String nomAccount,
                      @NotBlank String typeAccount, @NotBlank Double balance,
                      @NotBlank Double currentBalance, @NotBlank String status,
                      @NotBlank Date createdAt, @NotBlank Date updatedAt) {
        this.numAccount = numAccount;
        this.nomAccount = nomAccount;
        this.typeAccount = typeAccount;
        this.balance = balance;
        this.currentBalance = currentBalance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
