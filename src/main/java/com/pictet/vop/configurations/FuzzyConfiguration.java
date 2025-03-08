package com.pictet.vop.configurations;

import org.apache.lucene.search.FuzzyQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.intuit.fuzzymatcher.component.MatchService;

@Configuration
public class FuzzyConfiguration {
  
  @Bean
  public MatchService fuzzySearch() {
    return new MatchService();
  }
}   
