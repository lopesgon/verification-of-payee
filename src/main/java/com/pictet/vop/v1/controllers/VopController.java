package com.pictet.vop.v1.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.pictet.vop.v1.dtos.PayeeDTO;
import com.pictet.vop.v1.services.VerificationPayeeService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
public class VopController extends AbstractV1Controller {

  private final VerificationPayeeService verificationPayeeService;
  
  @PostMapping("verify")
  public String verificationPayee(@RequestBody PayeeDTO payee) {
      return verificationPayeeService.verify(payee);
  }
  
}
