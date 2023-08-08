package com.example.dailycarebe.user.statistics;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@RequiredArgsConstructor
public class UserStatisticsViewDto {
    private Double height;

    private Double weight;
}
