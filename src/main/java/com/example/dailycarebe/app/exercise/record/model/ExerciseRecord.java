package com.example.dailycarebe.app.exercise.record.model;

import com.example.dailycarebe.app.exercise.model.CourseType;
import com.example.dailycarebe.app.exercise.model.CourseWeekType;
import com.example.dailycarebe.app.exercise.model.Exercise;
import com.example.dailycarebe.app.exercise.model.ExerciseEvaluationType;
import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class ExerciseRecord extends AbstractAuditingEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Exercise exercise;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column
    private CourseType courseType;

    @Column
    private LocalDate today;

    @Column
    private CourseWeekType courseWeekType;

    @Column
    private Integer courseDay;

    @Enumerated(EnumType.STRING)
    @Column
    private ExerciseEvaluationType exerciseEvaluationType;
}
