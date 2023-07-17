package com.example.dailycarebe.auth.authorization.model;

import com.google.common.collect.Sets;

import java.util.Set;

public interface DefaultRole {
  // SYSTEM
  String SYS_ADM = "SYS_ADM";
  String SYS_USER = "SYS_USER";

  // USER
  String APP_ADM = "APP_ADM";
  String APP_USER = "APP_USER";


  Set<String> DEFAULT_APP_USER_ROLES = Sets.newHashSet(
    APP_USER
  );
}
