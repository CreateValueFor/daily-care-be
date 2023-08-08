package com.example.dailycarebe.app.food.dto;

import com.example.dailycarebe.base.orm.dto.AbstractEditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class FoodEditDto extends AbstractEditDto {
    private String subject;

    private LocalDateTime startTime;
}
