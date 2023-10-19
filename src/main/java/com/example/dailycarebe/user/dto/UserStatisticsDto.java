package com.example.dailycarebe.user.dto;

import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserStatisticsDto extends AbstractAuditingDto {
    List<Integer> goodStoolCountList;
    List<Integer> badStoolCountList;
    Integer exerciseCount;
    Integer averageGoodStoolCount;
    Integer averageGoodStoolCountFromLastWeek;
    Integer averageGoodStoolCountFromFirstWeek;

    Integer exerciseCountLastWeek;
    Integer exerciseCountLastWeekFromLastWeek;
    Integer exerciseCountLastWeekFromFirstWeek;

    Map<String, Integer> goodFood;
    Map<String, Integer> badFood;
}
