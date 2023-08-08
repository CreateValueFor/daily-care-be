package com.example.dailycarebe.app.food.dto;

import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class FoodViewDto extends AbstractAuditingDto {
    private String subject;

    private LocalDateTime startTime;
}
