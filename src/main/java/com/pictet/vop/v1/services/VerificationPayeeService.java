package com.pictet.vop.v1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Match;
import com.pictet.vop.configurations.properties.FuzzyProperties;
import com.pictet.vop.utils.FuzzyUtils;
import com.pictet.vop.v1.dtos.PayeeDTO;
import com.pictet.vop.v1.dtos.PayeeMatchResultDTO;
import com.pictet.vop.v1.types.IbanType;
import com.pictet.vop.v1.types.PayeeType;
import com.pictet.vop.v1.types.ResultEnum;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Service
public class VerificationPayeeService {
  private static final String PAYEE_QUERY = "SELECT * FROM payee WHERE iban = ?";

  private final JdbcTemplate jdbcTemplate;
  private final MatchService matchService;
  private final FuzzyProperties fuzzyProperties;

  public PayeeMatchResultDTO verify(PayeeDTO payee) {
    Document document = buildForPayee(payee);

    Map<Document, List<Match<Document>>> result = this.matchService.applyMatch(document,
        this.findPayeesForIban(payee.iban()));

    result.entrySet().forEach(entry -> {
      entry.getValue().forEach(match -> {
        log.debug("Data: " + match.getData() + " Matched With: " + match.getMatchedWith() + " Score: "
            + match.getScore().getResult());
      });
    });

    if (result.isEmpty()) {
      return new PayeeMatchResultDTO(ResultEnum.NO_MATCH, null);
    }

    if (result.size() > 1) {
      log.warn("Unexpected number of matches found: " + result.size() + ". Continuing processing with first match.");
    }

    List<PayeeType> matches = new ArrayList<>(1);

    var iterator = result.entrySet().iterator();
    while (iterator.hasNext()) {
      List<Match<Document>> resultMatches = iterator.next().getValue();
      for (Match<Document> match : resultMatches) {
        PayeeType payeeMatch = safeExtractPayeeTypeFromMatch(match);
        if (payeeMatch == null) {
          continue;
        }
        if (match.getScore().getResult() >= 1) {
          matches = List.of(payeeMatch);
          break;
        } else if (match.getScore().getResult() >= this.fuzzyProperties.getCloseMatchThreshold()) {
          matches.add(payeeMatch);
        }
      }
    }

    if (matches.size() == 1) {
      return new PayeeMatchResultDTO(ResultEnum.MATCH, matches);
    } else {
      return new PayeeMatchResultDTO(ResultEnum.CLOSE_MATCH, matches);
    }
  }

  private PayeeType safeExtractPayeeTypeFromMatch(Match<Document> match) {
    try {
      String value = (String) match.getData().getElements().iterator().next().getValue();
      return PayeeType.of(value);
    } catch (ClassCastException e) {
      log.error("Failed to cast payee type to String", e);
      return null;
    } catch (Exception e) {
      log.error("Unexpected exception while extracting safely payee type", e);
      return null;
    }
  }

  private Document buildForPayee(PayeeDTO payee) {
    return new Document.Builder(UUID.randomUUID().toString())
        .addElement(FuzzyUtils.buildNameElement(payee.payee().toString()))
        .createDocument();
  }

  private List<Document> findPayeesForIban(IbanType iban) {
    // todo query database to search for payees to match against
    // jdbcTemplate.execute(PAYEE_QUERY);
    // jdbcTemplate.queryForObject(PAYEE_QUERY, Object[].class, iban.toString());
    return List.of(
        new Document.Builder("1")
            .addElement(FuzzyUtils.buildNameElement("John Doe"))
            .createDocument(),
        new Document.Builder("2")
            .addElement(FuzzyUtils.buildNameElement("John Don"))
            .createDocument());
  }
}
