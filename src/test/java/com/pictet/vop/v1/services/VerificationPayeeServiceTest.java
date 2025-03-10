package com.pictet.vop.v1.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.intuit.fuzzymatcher.component.MatchService;
import com.pictet.vop.configurations.properties.FuzzyProperties;
import com.pictet.vop.repositories.IbanRepository;
import com.pictet.vop.v1.dtos.PayeeDTO;
import com.pictet.vop.v1.types.IbanType;
import com.pictet.vop.v1.types.PayeeType;
import com.pictet.vop.v1.types.ResultEnum;

public class VerificationPayeeServiceTest {

  private VerificationPayeeService verificationPayeeService;

  @Mock
  private IbanRepository ibanRepositoryMock;
  @Mock
  private MatchService matchServiceMock;

  @BeforeEach
  void before() {
    var props = new FuzzyProperties();
    props.setCloseMatchThreshold(0.8);

    ibanRepositoryMock = Mockito.mock(IbanRepository.class);
    verificationPayeeService = new VerificationPayeeService(ibanRepositoryMock, new MatchService(), props);
  }

  @Test
  void test() {
    // Given
    var payee = new PayeeDTO(IbanType.of("CH9300762011623852957"), PayeeType.of("John Don"));
    Mockito.when(ibanRepositoryMock.findPayeesForIban(any()))
        .thenReturn(List.of(new PayeeDTO("1", IbanType.of("CH9300762011623852957"), PayeeType.of("John Done"))));

    // When
    var result = verificationPayeeService.verify(payee);

    // Then
    Assertions.assertEquals(ResultEnum.MATCH, result.result());
  }
}
