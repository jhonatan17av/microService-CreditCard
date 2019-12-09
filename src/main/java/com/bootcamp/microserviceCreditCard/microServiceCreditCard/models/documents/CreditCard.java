package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents;

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
    private Double balance;
    @NotBlank
    private Double currentBalance;
    @NotBlank
    private String status;
    @NotBlank
    private Date createAt;
    @NotBlank
    private Date updateAt;

    public CreditCard() {
    }

    public CreditCard(@NotBlank String numAccount, @NotBlank String nomAccount, @NotBlank Double balance, @NotBlank Double currentBalance, @NotBlank String status, @NotBlank Date createAt, @NotBlank Date updateAt) {
        this.numAccount = numAccount;
        this.nomAccount = nomAccount;
        this.balance = balance;
        this.currentBalance = currentBalance;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }

    public String getNomAccount() {
        return nomAccount;
    }

    public void setNomAccount(String nomAccount) {
        this.nomAccount = nomAccount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
