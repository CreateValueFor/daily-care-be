package com.example.dailycarebe.app.movement.dto;

import com.example.dailycarebe.base.orm.dto.AbstractRegisterDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovementRegisterDto extends AbstractRegisterDto {
    private LocalDate localDate;

    private List<MovementDetailRegisterDto> movementDetails;
}
