package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class MovPayFromAccount {

  @NotEmpty(message = "The field numCreditCard shouldn't be empty")
  private String numCreditCard;
  @NotEmpty(message = "The field numAccount shouldn't be empty")
  private String numAccount;
  @NotEmpty(message = "The field typeMovement shouldn't be empty")
  private String typeMovement;
  @NotEmpty(message = "The field balanceTransaction shouldn't be empty")
  private Double balanceTransaction;
  @NotEmpty(message = "The field commission shouldn't be empty")
  private Double commission;
  @NotBlank(message = "The field createdAt shouldn't be empty")
  @JsonFormat(pattern = "dd-MMMM-yy")
  private Date createdAt;
}
