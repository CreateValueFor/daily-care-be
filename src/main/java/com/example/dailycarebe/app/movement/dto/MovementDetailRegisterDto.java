package com.example.dailycarebe.app.movement.dto;

import com.example.dailycarebe.app.movement.model.MovementType;
import com.example.dailycarebe.base.orm.dto.AbstractRegisterDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovementDetailRegisterDto extends AbstractRegisterDto {
    private LocalTime localTime;

    private MovementType movementType;
}
