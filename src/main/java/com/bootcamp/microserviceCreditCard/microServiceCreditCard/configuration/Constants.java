package com.bootcamp.microserviceCreditCard.microServiceCreditCard.configuration;

public class Constants {

  private static Long num = (long) (1000000000 * Math.random());

  public static final String NUM_ACCOUNT = String.valueOf(num);
  public static final String NOM_ACCOUNT = "Tarjeta de Credito";

}
