package com.example.dailycarebe.base.orm.model.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class JpaLongSetConverter extends JpaJsonConverter<Set<Long>> {

  @Override
  protected TypeReference<Set<Long>> getTypeReference() {
    return new TypeReference<>() {
    };
  }
}
