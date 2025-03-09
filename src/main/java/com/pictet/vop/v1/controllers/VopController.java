package com.pictet.vop.v1.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pictet.vop.v1.dtos.PayeeDTO;
import com.pictet.vop.v1.dtos.PayeeMatchResultDTO;
import com.pictet.vop.v1.services.VerificationPayeeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class VopController extends AbstractV1Controller {

  private final VerificationPayeeService verificationPayeeService;

  @PostMapping("verify")
  public PayeeMatchResultDTO verificationPayee(@RequestBody PayeeDTO payee) {
    return verificationPayeeService.verify(payee);
  }

}
