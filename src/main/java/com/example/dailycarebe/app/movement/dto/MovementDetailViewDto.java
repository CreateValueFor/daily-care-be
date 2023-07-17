package com.example.dailycarebe.app.movement.dto;

import com.example.dailycarebe.app.movement.model.MovementType;
import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class MovementDetailViewDto extends AbstractAuditingDto {
    private LocalTime localTime;

    private MovementType movementType;
}
