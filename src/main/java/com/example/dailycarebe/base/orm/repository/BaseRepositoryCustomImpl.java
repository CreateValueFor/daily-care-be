package com.example.dailycarebe.base.orm.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.example.dailycarebe.exception.ResourceNotFoundException;
import com.example.dailycarebe.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class BaseRepositoryCustomImpl {
  protected final JPAQueryFactory queryFactory;


  protected BooleanExpression appendExpression(BooleanExpression expression, BooleanExpression expressionToAppend) {
    if (expression == null) {
      expression = expressionToAppend;
    } else {
      expression = expression.and(expressionToAppend);
    }

    return expression;
  }

  protected BooleanExpression appendOrExpression(BooleanExpression expression, BooleanExpression expressionToAppend) {
    if (expression == null) {
      expression = expressionToAppend;
    } else {
      expression = expression.or(expressionToAppend);
    }

    return expression;
  }
}
