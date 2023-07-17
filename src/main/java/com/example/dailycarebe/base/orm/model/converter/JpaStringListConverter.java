package com.example.dailycarebe.base.orm.model.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JpaStringListConverter extends JpaJsonConverter<List<String>> {

  @Override
  protected TypeReference<List<String>> getTypeReference() {
    return new TypeReference<List<String>>() {};
  }
}
