package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Document(collection = "movements")
public class Movement {

    @NotBlank
    @Id
    private String id;
    @NotBlank
    private String numAccount;
    @NotBlank
    private String typeMovement;
    @NotBlank
    private Double balanceTransaction;
    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;

    public Movement() {
    }

    public Movement(@NotBlank String numAccount, @NotBlank String typeMovement, @NotBlank Double balanceTransaction, @NotBlank Date createdAt) {
        this.numAccount = numAccount;
        this.typeMovement = typeMovement;
        this.balanceTransaction = balanceTransaction;
        this.createdAt = createdAt;
    }
}
