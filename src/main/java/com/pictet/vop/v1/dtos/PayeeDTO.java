package com.pictet.vop.v1.dtos;

import com.pictet.vop.v1.types.IbanType;
import com.pictet.vop.v1.types.PayeeType;

public record PayeeDTO(IbanType iban, PayeeType payee) {
  
}