package com.example.dailycarebe.util;

import org.passay.CharacterData;
import org.passay.*;

public class PasswordUtil {
  public static String generatePassayPassword() {
    PasswordGenerator gen = new PasswordGenerator();
    CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
    CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
    lowerCaseRule.setNumberOfCharacters(2);

    CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
    CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
    upperCaseRule.setNumberOfCharacters(2);

    CharacterData digitChars = EnglishCharacterData.Digit;
    CharacterRule digitRule = new CharacterRule(digitChars);
    digitRule.setNumberOfCharacters(2);

    CharacterData specialChars = new CharacterData() {
      public String getErrorCode() {
        return CharacterCharacteristicsRule.ERROR_CODE;
      }

      public String getCharacters() {
        return "!@#$%^&*()_+";
      }
    };
    CharacterRule splCharRule = new CharacterRule(specialChars);
    splCharRule.setNumberOfCharacters(2);

    String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
      upperCaseRule, digitRule);
    return password;
  }

//유저 마이그레이션과 충돌하지 않게 고도몰 es_member memberId 를 php uniqueId() 함수 구현
  private final static long currentTimeNanosOffset = (System.currentTimeMillis() * 1000000) - System.nanoTime();
  public static long currentTimeNanos() {
    return System.nanoTime() + currentTimeNanosOffset;
  }
  public static String uniqueId() {
    long nanos = currentTimeNanos();
    return Long.toHexString(Long.parseLong(String.valueOf(nanos).substring(0,10)))
      + Long.toHexString(Long.parseLong(String.valueOf(nanos).substring(10,16)));
  }
  public static String uniqueId(String prefix) {
    return prefix + "_" + uniqueId();
  }
}
