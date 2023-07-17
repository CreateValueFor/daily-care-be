package com.example.dailycarebe.base.orm.model.converter;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class StringListConverter extends JpaJsonConverter<List<String>>{
  @Override
  protected TypeReference<List<String>> getTypeReference() {
    return new TypeReference<>() {
    };
  }
}
