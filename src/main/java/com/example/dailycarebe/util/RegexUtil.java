package com.example.dailycarebe.util;

import java.util.regex.Pattern;

public class RegexUtil {

  public static final Pattern imgTagPattern = Pattern.compile("src\\s*=\\s*\"(.+?)\"");
}
