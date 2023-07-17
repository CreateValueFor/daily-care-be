package com.example.dailycarebe.base;

import com.example.dailycarebe.util.HashidsUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseComponent {
  @Value("${spring.application.name}")
  @Getter
  private String applicationName;

  @Autowired
  @Getter(AccessLevel.PROTECTED)
  private Environment env;

  @Autowired
  @Getter(AccessLevel.PROTECTED)
  private ApplicationContext applicationContext;

  @Autowired
  @Getter(AccessLevel.PROTECTED)
  private PlatformTransactionManager platformTransactionManager;

//  @Autowired
//  @Getter(AccessLevel.PROTECTED)
//  private JpaTableMetaData metadata;

  protected boolean isLocalEnv() {
    return env.getActiveProfiles()[0].startsWith("local");
  }

  protected boolean isDevEnv() {
    return env.getActiveProfiles()[0].contains("dev");
  }

  protected boolean isProductionEnv() {
    return env.acceptsProfiles(Profiles.of("prod", "preprod"));
  }

  protected <T> T getSelf(T instance) {
    return applicationContext.getBean((Class<T>)this.getClass());
  }

  protected <T> T getBean(Class<T> clazz) {
    return applicationContext.getBean(clazz);
  }

  protected Object getBean(String beanName) {
    return applicationContext.getBean(beanName);
  }

  protected void executeWithNewTransaction(Consumer<Void> function) {
    TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
    transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    transactionTemplate.execute((TransactionStatus transactionStatus) -> {
      try {
        function.accept(null);
      } catch (Exception ex) {
        throw new RuntimeException("Unexpected exception", ex);
      }
      return null;
    });
  }

  protected <RESULT> RESULT executeAndGetWithNewTransaction(Supplier<RESULT> function) {
    TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
    transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    return transactionTemplate.execute((TransactionStatus transactionStatus) -> {
      try {
        return function.get();
      } catch (Exception ex) {
        throw new RuntimeException("Unexpected exception", ex);
      }
    });
  }

  protected Set<String> convertToIdHash(Set<Long> ids) {
    return ids.stream()
      .map(this::convertToIdHash)
      .collect(Collectors.toSet());
  }

  protected String convertToIdHash(Long id) {
    return id == null ? null : HashidsUtil.encodeNumber(id);
  }

  protected long convertToId(String idHash) {
    return HashidsUtil.decodeNumber(idHash);
  }
}
