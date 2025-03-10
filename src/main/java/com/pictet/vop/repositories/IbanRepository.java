package com.pictet.vop.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pictet.vop.v1.dtos.PayeeDTO;
import com.pictet.vop.v1.types.IbanType;
import com.pictet.vop.v1.types.PayeeType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class IbanRepository {
  private static final String PAYEE_QUERY = "SELECT * FROM payee WHERE iban = ?";

  private final JdbcTemplate jdbcTemplate;

  public List<PayeeDTO> findPayeesForIban(IbanType iban) {
    return this.jdbcTemplate.query(PAYEE_QUERY, (rs, rowNum) -> {
      return new PayeeDTO(IbanType.of(rs.getString("iban")), PayeeType.of(rs.getString("name")));
    }, iban.toString());
  }
}
