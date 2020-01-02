/*.
 *
 */
package com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AccountDto {

  @NotEmpty(message = "Campo nomBank no puede ser vacio")
  private String nomBank;
  @NotEmpty(message = "Campo numAccount no puede ser vacio")
  private String numAccount;
  @NotEmpty(message = "Campo nomAccount no puede ser vacio")
  private String nomAccount;
  @NotEmpty(message = "Campo typeAccount no puede ser vacio")
  private String typeAccount;
  @NotEmpty(message = "Campo status no puede ser vacio")
  private String status;

  public AccountDto() {
  }

  public AccountDto(@NotEmpty(message = "Campo nomBank no puede ser vacio")
                        String nomBank,
                    @NotEmpty(message = "Campo numAccount no puede ser vacio")
                        String numAccount,
                    @NotEmpty(message = "Campo nomAccount no puede ser vacio")
                        String nomAccount,
                    @NotEmpty(message = "Campo typeAccount no puede ser vacio")
                        String typeAccount,
                    @NotEmpty(message = "Campo status no puede ser vacio") String status) {
    this.nomBank = nomBank;
    this.numAccount = numAccount;
    this.nomAccount = nomAccount;
    this.typeAccount = typeAccount;
    this.status = status;
  }
}
