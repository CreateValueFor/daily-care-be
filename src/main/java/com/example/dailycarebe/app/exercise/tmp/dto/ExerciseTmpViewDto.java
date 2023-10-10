package com.example.dailycarebe.app.exercise.tmp.dto;

import com.example.dailycarebe.app.exercise.dto.ExerciseRecordViewDto;
import com.example.dailycarebe.app.exercise.model.ExerciseEvaluationType;
import com.example.dailycarebe.app.exercise.record.model.ExerciseRecord;
import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import com.example.dailycarebe.user.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class ExerciseTmpViewDto extends AbstractAuditingDto {
    private ExerciseRecordViewDto exerciseRecord;
    private LocalDate today;
    private ExerciseEvaluationType exerciseEvaluationType;
    private Boolean complete;
}
