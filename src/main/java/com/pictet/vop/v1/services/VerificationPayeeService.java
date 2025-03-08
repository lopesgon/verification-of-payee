package com.pictet.vop.v1.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.pictet.vop.v1.dtos.PayeeDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class VerificationPayeeService {

  private final JdbcTemplate jdbcTemplate;

  public String verify(PayeeDTO payee) {
    if (jdbcTemplate == null) {
      log.error("JdbcTemplate is null");
    }
    log.info(payee);
    return "verified";
  }
}
