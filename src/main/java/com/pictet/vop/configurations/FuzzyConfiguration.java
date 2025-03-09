package com.pictet.vop.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.intuit.fuzzymatcher.component.MatchService;

@Configuration
public class FuzzyConfiguration {
  
  @Bean
  public MatchService matchService() {
    return new MatchService();
  }
}   
