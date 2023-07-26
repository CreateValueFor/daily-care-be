package com.example.dailycarebe.app.exercise.dto;

import com.example.dailycarebe.app.exercise.model.CourseType;
import com.example.dailycarebe.app.exercise.model.PostureType;
import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class ExerciseViewDto extends AbstractAuditingDto {
    private String url;

    private String name;

    private PostureType postureType;

    private CourseType courseType;
}
