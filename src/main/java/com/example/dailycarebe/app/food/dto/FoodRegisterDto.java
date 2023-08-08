package com.example.dailycarebe.app.food.dto;

import com.example.dailycarebe.base.orm.dto.AbstractRegisterDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class FoodRegisterDto extends AbstractRegisterDto {
    private String subject;

    private LocalDateTime startTime;
}
