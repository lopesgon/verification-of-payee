package com.pictet.vop.v1.types;

import com.fasterxml.jackson.annotation.JsonCreator;

public class IbanType {
  
  private String iban;
  
  private IbanType(String iban) {
    this.iban = iban;
  }

  @JsonCreator
  public static IbanType of(String iban) {
    return new IbanType(iban);
  }

  public String toString() {
    return iban;
  }
}
