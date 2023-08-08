package com.example.dailycarebe.app.exercise.record.dto;

import com.example.dailycarebe.app.exercise.model.ExerciseEvaluationType;
import com.example.dailycarebe.base.orm.dto.AbstractEditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExerciseRecordEditDto extends AbstractEditDto {
    private String uuid;

    private ExerciseEvaluationType exerciseEvaluationType;
}
