package com.pictet.vop.v1.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import com.intuit.fuzzymatcher.component.MatchService;
import com.pictet.vop.configurations.properties.FuzzyProperties;
import com.pictet.vop.v1.dtos.PayeeDTO;
import com.pictet.vop.v1.types.IbanType;
import com.pictet.vop.v1.types.PayeeType;
import com.pictet.vop.v1.types.ResultEnum;

public class VerificationPayeeServiceTest {

  private VerificationPayeeService verificationPayeeService;

  @Mock
  private JdbcTemplate jdbcTemplateMock;

  @BeforeEach
  void before() {
    // Mockito.mock(JdbcTemplate.class);
    var props = new FuzzyProperties();
    props.setCloseMatchThreshold(0.8);
    verificationPayeeService = new VerificationPayeeService(jdbcTemplateMock, new MatchService(), props);
  }

  @Test
  void test() {
    // Given
    var payee = new PayeeDTO(IbanType.of("CH9300762011623852957"), PayeeType.of("John Don"));

    // When
    var result = verificationPayeeService.verify(payee);

    // Then
    Assertions.assertEquals(ResultEnum.MATCH, result.result());
  }
}
