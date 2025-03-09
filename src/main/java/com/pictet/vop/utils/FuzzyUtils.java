package com.pictet.vop.utils;

import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.ElementType;
import com.intuit.fuzzymatcher.function.TokenizerFunction;

public class FuzzyUtils {

  @SuppressWarnings({ "unchecked" })
  public static Element<String> buildNameElement(String name) {
    Element.Builder<String> builder = new Element.Builder<String>();

    builder
        .setType(ElementType.NAME)
        .setValue(name)
        // .setThreshold(0.8)
        .setTokenizerFunction(TokenizerFunction.triGramTokenizer());

    return builder.createElement();
  }
}
