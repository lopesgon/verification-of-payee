package com.pictet.vop.v1.types;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PayeeType {
  
  private String payee;
  
  private PayeeType(String payee) {
    this.payee = payee;
  }

  @JsonCreator
  public static PayeeType of(String payee) {
    return new PayeeType(payee);
  }

  public String toString() {
    return payee;
  }
}
