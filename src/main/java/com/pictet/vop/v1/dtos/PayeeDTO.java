package com.pictet.vop.v1.dtos;

import java.util.UUID;

import com.pictet.vop.v1.types.IbanType;
import com.pictet.vop.v1.types.PayeeType;

public record PayeeDTO(String key, IbanType iban, PayeeType payee) {

  public PayeeDTO(String key, IbanType iban, PayeeType payee) {
    this.key = key;
    this.iban = iban;
    this.payee = payee;
  }
  public PayeeDTO(IbanType iban, PayeeType payee) {
    this(UUID.randomUUID().toString(), iban, payee);
  }
}