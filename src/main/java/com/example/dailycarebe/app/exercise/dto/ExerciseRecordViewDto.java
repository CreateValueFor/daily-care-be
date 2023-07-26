package com.example.dailycarebe.app.exercise.dto;

import com.example.dailycarebe.app.exercise.model.CourseType;
import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class ExerciseRecordViewDto extends AbstractAuditingDto {
    private ExerciseViewDto exercise;

    private CourseType courseType;

    private LocalDate today;
}
