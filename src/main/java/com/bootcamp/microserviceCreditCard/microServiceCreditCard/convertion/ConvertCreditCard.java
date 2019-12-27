package com.bootcamp.microserviceCreditCard.microServiceCreditCard.convertion;

import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.CreditCard;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.documents.Movement;
import com.bootcamp.microserviceCreditCard.microServiceCreditCard.models.dto.CreditCardDto;
import org.springframework.stereotype.Controller;

@Controller
public class ConvertCreditCard {

	public CreditCardDto toCreditCardDto(CreditCard creditCard) {
		CreditCardDto dto = new CreditCardDto();
		dto.setNumAccount(creditCard.getNumAccount());
		dto.setNomAccount(creditCard.getNomAccount());
		dto.setTypeAccount(creditCard.getTypeAccount());
		dto.setBalance(creditCard.getBalance());
		dto.setCurrentBalance(creditCard.getCurrentBalance());
		dto.setStatus(creditCard.getStatus());
		dto.setCreatedAt(creditCard.getCreatedAt());
		dto.setUpdatedAt(creditCard.getUpdatedAt());
		return dto;
	}
	
	public CreditCard toCreditCard(CreditCardDto dto) {
		CreditCard creditCard = new CreditCard();
		creditCard.setNomBank(dto.getNomBank());
		creditCard.setNumAccount(dto.getNumAccount());
		creditCard.setNomAccount(dto.getNomAccount());
		creditCard.setTypeAccount(dto.getTypeAccount());
		creditCard.setBalance(dto.getBalance());
		creditCard.setCurrentBalance(dto.getCurrentBalance());
		creditCard.setStatus(dto.getStatus());
		creditCard.setCreatedAt(dto.getCreatedAt());
		creditCard.setUpdatedAt(dto.getUpdatedAt());
		return creditCard;
	}

	public Movement toMovement(CreditCardDto dto) {
		Movement movement = new Movement();
		movement.setNumAccount(dto.getNumAccount());
		movement.setNumAccount(dto.getNumAccount());
		movement.setNumAccount(dto.getNumAccount());
		return movement;
	}

	
}
