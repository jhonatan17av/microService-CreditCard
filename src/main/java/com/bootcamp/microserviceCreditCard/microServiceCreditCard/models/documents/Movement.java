package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Document(collection = "movements")
public class Movement {

    @NotBlank
    @Id
    private String id;
    @NotEmpty(message = "Campo numAccount no puede ser vacio")
    private String numAccount;
    @NotEmpty(message = "Campo typeMovement no puede ser vacio")
    private String typeMovement;
    @NotEmpty(message = "Campo balanceTransaction no puede ser vacio")
    private Double balanceTransaction;
    @NotEmpty(message = "Campo commission no puede ser vacio")
    private Double commission;
    @NotEmpty(message = "Campo createdAt no puede ser vacio")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;

    public Movement() {
    }

    public Movement(
            @NotEmpty(message = "Campo numAccount no puede ser vacio") String numAccount,
            @NotEmpty(message = "Campo typeMovement no puede ser vacio") String typeMovement,
            @NotEmpty(message = "Campo balanceTransaction no puede ser vacio")
                    Double balanceTransaction,
            @NotEmpty(message = "Campo commission no puede ser vacio") Double commission,
            @NotEmpty(message = "Campo createdAt no puede ser vacio") Date createdAt) {
        this.numAccount = numAccount;
        this.typeMovement = typeMovement;
        this.balanceTransaction = balanceTransaction;
        this.commission = commission;
        this.createdAt = createdAt;
    }
}
