package com.example.dailycarebe.app.movement.dto;

import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class MovementViewDto extends AbstractAuditingDto {
    private LocalDate localDate;

    private Set<MovementDetailViewDto> movementDetails;
}
