package com.pictet.vop.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "fuzzy")
public class FuzzyProperties {

  private double closeMatchThreshold;

  @PostConstruct
  public void validate() {
    if (closeMatchThreshold < 0.0 || closeMatchThreshold > 1.0) {
      throw new IllegalArgumentException("property fuzzy.close-match-threshold must be between 0.0 and 1.0!");
    }
  }
}
