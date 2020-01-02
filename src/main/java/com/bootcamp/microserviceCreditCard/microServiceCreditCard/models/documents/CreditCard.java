package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Document(collection = "creditCards")
public class CreditCard {

    @NotBlank
    @Id
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

    public CreditCard() {
    }

    public CreditCard(@NotEmpty(message = "Campo nomBank no puede ser vacio")
                              String nomBank,
                      @NotEmpty(message = "Campo numAccount no puede ser vacio")
                              String numAccount,
                      @NotEmpty(message = "Campo nomAccount no puede ser vacio")
                              String nomAccount,
                      @NotEmpty(message = "Campo typeAccount no puede ser vacio")
                              String typeAccount,
                      @NotEmpty(message = "Campo balance no puede ser vacio")
                      @Min(value = 0, message = "Valor minimo 0")
                              Double balance,
                      @NotEmpty(message = "Campo currentBalance no puede ser vacio")
                              Double currentBalance,
                      @NotEmpty(message = "Campo status no puede ser vacio")
                              String status,
                      @NotBlank
                              Date createdAt,
                      @NotBlank
                              Date updatedAt,
                      @NotEmpty(message = "Campo cantTransactions no puede ser vacio")
                              Integer cantTransactions,
                      @NotBlank
                          Date datePay) {
        this.nomBank = nomBank;
        this.numAccount = numAccount;
        this.nomAccount = nomAccount;
        this.typeAccount = typeAccount;
        this.balance = balance;
        this.currentBalance = currentBalance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.cantTransactions = cantTransactions;
        this.datePay = datePay;
    }
}
