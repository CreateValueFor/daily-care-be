package com.example.dailycarebe.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.*;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.temporal.ChronoUnit.YEARS;

public class DateUtil {
  public static final ZoneId ZONE_SEOUL = ZoneId.of("Asia/Seoul");

  public static final LocalDate INITIAL_BEGIN_DATE = LocalDate.of(1970, 1, 1);
  public static final LocalDateTime INITIAL_BEGIN_DATETIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
  public static final LocalDate INITIAL_END_DATE = LocalDate.of(9999, 12, 31);
  public static final LocalDateTime INITIAL_END_DATETIME = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

  public static final DateTimeFormatter DATE_FORMAT_KOR_YMD = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN);
  public static final DateTimeFormatter DATE_FORMAT_KOR_MD = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN);
  public static final DateTimeFormatter DATE_FORMAT_KOR_MDH = DateTimeFormatter.ofPattern("MM월 dd일 HH시", Locale.KOREAN);
  public static final DateTimeFormatter DATE_FORMAT_KOR_MDHM = DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분", Locale.KOREAN);
  public static final DateTimeFormatter DATE_FORMAT_KOR_YMD_E = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E)", Locale.KOREAN);
  public static final DateTimeFormatter DATE_FORMAT_KOR_MD_E = DateTimeFormatter.ofPattern("MM월 dd일(E)", Locale.KOREAN);
  public static final DateTimeFormatter DATE_FORMAT_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  public static final DateTimeFormatter DATE_FORMAT_YMD_NODEL = DateTimeFormatter.ofPattern("yyyyMMdd");
  public static final DateTimeFormatter DATE_FORMAT_SHORT_YMD_NODEL = DateTimeFormatter.ofPattern("yyMMdd");
  public static final DateTimeFormatter DATE_FORMAT_YM = DateTimeFormatter.ofPattern("yyyy-MM");
  public static final DateTimeFormatter DATE_FORMAT_MD = DateTimeFormatter.ofPattern("MM-dd");
  public static final DateTimeFormatter DATE_FORMAT_HM = DateTimeFormatter.ofPattern("HH:mm");
  public static final DateTimeFormatter DATE_FORMAT_DOT_YMD_HM = DateTimeFormatter.ofPattern("yyyy. MM. dd. HH:mm");
  public static final DateTimeFormatter DATE_FORMAT_KOR_YMDHMS_NODEL = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


  public static void setTimeZoneToKST() {
    TimeZone.setDefault(TimeZone.getTimeZone(ZONE_SEOUL));
  }

  public static ZonedDateTime localDateToZonedDateTime(LocalDate date) {
    return date.atStartOfDay(ZONE_SEOUL);
  }

  public static LocalDate parseDateString(String dateString) {
    try {
      return DateUtils.parseDate(dateString).toInstant().atZone(ZONE_SEOUL).toLocalDate();
    } catch (ParseException e) {
      return null;
    }
  }

  public static boolean isAfterOrEqual(LocalDateTime source, LocalDateTime target) {
    return source.isAfter(target) || source.equals(target);
  }

  public static boolean isBeforeOrEqual(LocalDateTime source, LocalDateTime target) {
    return source.isBefore(target) || source.equals(target);
  }

  public static boolean isAfterOrEqual(LocalDate source, LocalDate target) {
    return source.isAfter(target) || source.equals(target);
  }

  public static boolean isBeforeOrEqual(LocalDate source, LocalDate target) {
    return source.isBefore(target) || source.equals(target);
  }

  public static boolean isAfterOrEqual(LocalTime source, LocalTime target) {
    return source.isAfter(target) || source.equals(target);
  }

  public static boolean isBeforeOrEqual(LocalTime source, LocalTime target) {
    return source.isBefore(target) || source.equals(target);
  }

  public static int getYearsBetween(LocalDate before, LocalDate after) {
    long years;
    if (before.isAfter(after)) {
      years = YEARS.between(after, before);
    } else {
      years = YEARS.between(before, after);
    }
    return (int) years;
  }

  public static int getDaysBetween(LocalDate before, LocalDate after) {
    return (int) ChronoUnit.DAYS.between(before, after);
  }

  public static int getDaysBetweenInclusive(LocalDate before, LocalDate after) {
    return (int) ChronoUnit.DAYS.between(before, after) + 1;
  }

  public static boolean isSatOrSunday(LocalDate date) {
    return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
  }

  public static LocalDate firstDayOfYear(int year) {
    return LocalDate.ofYearDay(year, 1);
  }

  public static LocalDate lastDayOfYear(int year) {
    boolean leapYear = IsoChronology.INSTANCE.isLeapYear(year);
    if (leapYear) {
      return LocalDate.ofYearDay(year, 366);
    } else {
      return LocalDate.ofYearDay(year, 365);
    }
  }

  public static LocalDate firstDayOfYear() {
    return firstDayOfYear(LocalDate.now());
  }

  public static LocalDate lastDayOfYear() {
    return lastDayOfYear(LocalDate.now());
  }

  public static LocalDate firstDayOfYear(LocalDate date) {
    return firstDayOfYear(date.getYear());
  }

  public static LocalDate lastDayOfYear(LocalDate date) {
    return lastDayOfYear(date.getYear());
  }

  public static LocalDate firstDayOfMonth() {
    return firstDayOfMonth(LocalDate.now());
  }

  public static LocalDate firstDayOfMonth(LocalDate date) {
    return date.with(TemporalAdjusters.firstDayOfMonth());
  }

  public static LocalDate lastDayOfMonth() {
    return lastDayOfMonth(LocalDate.now());
  }

  public static LocalDate lastDayOfMonth(LocalDate date) {
    return date.with(TemporalAdjusters.lastDayOfMonth());
  }

  public static boolean isBetweenExclusive(LocalDate target, LocalDate from, LocalDate to) {
    return isAfterOrEqual(target, from) && isBefore(target, to);
  }

  public static boolean isBetweenInclusive(LocalDateTime target, LocalDateTime from, LocalDateTime to) {
    return isAfterOrEqual(target, from) && isBeforeOrEqual(target, to);
  }

  public static boolean isBetweenInclusive(LocalDate target, LocalDate from, LocalDate to) {
    return isAfterOrEqual(target, from) && isBeforeOrEqual(target, to);
  }

  public static boolean isBetweenInclusive(LocalTime target, LocalTime from, LocalTime to) {
    return isAfterOrEqual(target, from) && isBeforeOrEqual(target, to);
  }

  public static boolean isBefore(LocalDate source, LocalDate target) {
    return source.isBefore(target);
  }

  public static boolean isBefore(LocalDateTime source, LocalDateTime target) {
    return source.isBefore(target);
  }

  public static boolean isAfter(LocalDate source, LocalDate target) {
    return source.isAfter(target);
  }

  public static boolean isAfter(LocalDateTime source, LocalDateTime target) {
    return source.isAfter(target);
  }

  public static LocalTime max(LocalTime time1, LocalTime time2) {
    return time1.compareTo(time2) >= 0 ? time1 : time2;
  }

  public static LocalDateTime max(LocalDateTime time1, LocalDateTime time2) {
    return time1.compareTo(time2) >= 0 ? time1 : time2;
  }

  public static LocalDate max(LocalDate date1, LocalDate date2) {
    return date1.compareTo(date2) >= 0 ? date1 : date2;
  }

  public static LocalTime min(LocalTime time1, LocalTime time2) {
    return time1.compareTo(time2) <= 0 ? time1 : time2;
  }

  public static LocalDateTime min(LocalDateTime time1, LocalDateTime time2) {
    return time1.compareTo(time2) <= 0 ? time1 : time2;
  }

  public static LocalDate min(LocalDate date1, LocalDate date2) {
    return date1.compareTo(date2) <= 0 ? date1 : date2;
  }

  public static boolean isSameYear(LocalDate target, LocalDate source) {
    return target.getYear() == source.getYear();
  }

  public static boolean isSameMonthAndDay(LocalDate target, LocalDate source) {
    return isSameMonth(target, source) && isSameDayOfMonth(target, source);
  }

  public static boolean isSameMonth(LocalDate target, LocalDate source) {
    return target.getMonth().getValue() == source.getMonth().getValue();
  }

  public static boolean isSameDayOfMonth(LocalDate target, LocalDate source) {
    return target.getDayOfMonth() == source.getDayOfMonth();
  }

  public static boolean isSameMonthAndDayOrLastDay(LocalDate target, LocalDate source) {
    return isSameMonth(target, source) && isSameDayOfMonthOrLastDay(target, source);
  }

  public static boolean isSameDayOfMonthOrLastDay(LocalDate target, LocalDate source) {
    boolean isSameDay = isSameDayOfMonth(target, source);

    if (!isSameDay) {
      if (target.getDayOfMonth() < source.getDayOfMonth()) {
        isSameDay = DateUtil.isLastDayOfMonth(target) && DateUtil.isLastDayOfMonth(source);
      }
    }

    return isSameDay;
  }

  private static boolean isLastDayOfMonth(LocalDate target) {
    return target.lengthOfMonth() == target.getDayOfMonth();
  }

  public static int getDaysOfYear(int year) {
    boolean leapYear = IsoChronology.INSTANCE.isLeapYear(year);
    if (leapYear) {
      return 366;
    } else {
      return 365;
    }
  }

  public static int getDaysOfThisYear() {
    boolean leapYear = IsoChronology.INSTANCE.isLeapYear(LocalDate.now().getYear());
    if (leapYear) {
      return 366;
    } else {
      return 365;
    }
  }

  public static boolean isFirstDayOfYear(LocalDate date) {
    return date.getDayOfYear() == 1;
  }

  public static boolean isFirstDayOfMonth(LocalDate date) {
    return date.getDayOfMonth() == 1;
  }

  public static boolean isToday(LocalDate date) {
    LocalDate today = LocalDate.now();
    return date != null && date.equals(today);
  }

  public static boolean isPast(LocalDate date) {
    LocalDate today = LocalDate.now();
    return date != null && date.isBefore(today);
  }

  public static boolean isFuture(LocalDate date) {
    LocalDate today = LocalDate.now();
    return date != null && date.isAfter(today);
  }

  public static boolean isFutureOrToday(LocalDate date) {
    LocalDate today = LocalDate.now();
    return date != null && isAfterOrEqual(date, today);
  }

  public static boolean isPastOrToday(LocalDate date) {
    LocalDate today = LocalDate.now();
    return date != null && isBeforeOrEqual(date, today);
  }

  public static boolean isThisYear(LocalDate date) {
    if (date == null) {
      return false;
    }

    return isSameYear(date, LocalDate.now());
  }

  public static boolean isMultipleYearBy(LocalDate targetDate, LocalDate onboardingDate, int years) {
    if (onboardingDate == null) {
      return false;
    }
    int workingYears = targetDate.getYear() - onboardingDate.getYear();
    return workingYears % years == 0;
  }

  public static boolean isAfterYearsBy(LocalDate targetDate, LocalDate onboardingDate, int years) {
    if (onboardingDate == null) {
      return false;
    }
    int workingYears = targetDate.getYear() - onboardingDate.getYear();
    return workingYears == years;
  }

  public static DayOfWeek getDayOfWeekFromDayString(String day) {
    switch (day) {
      case "월":
        return DayOfWeek.MONDAY;
      case "화":
        return DayOfWeek.TUESDAY;
      case "수":
        return DayOfWeek.WEDNESDAY;
      case "목":
        return DayOfWeek.THURSDAY;
      case "금":
        return DayOfWeek.FRIDAY;
      case "토":
        return DayOfWeek.SATURDAY;
      case "일":
        return DayOfWeek.SUNDAY;
      default:
        return null;
    }
  }

  public static LocalDateTime withMinTime(LocalDate dateTime) {
    return LocalDateTime.of(dateTime, LocalTime.MIN);
  }

  public static LocalDateTime withMaxTime(LocalDate dateTime) {
    return LocalDateTime.of(dateTime, LocalTime.MAX);
  }

  public static String format(LocalDate date) {
    return DATE_FORMAT_YMD.format(date);
  }

  public static String format(LocalDateTime date) {
    return DateTimeFormatter.ISO_DATE_TIME.format(date);
  }

  public static LocalTime averageTimeBetween(LocalTime from, LocalTime to) {
    Duration diff = Duration.between(from, to);
    return from.plus(diff.dividedBy(2));
  }
}
