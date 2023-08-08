package com.example.dailycarebe.app.exercise.initiate.model;

import com.example.dailycarebe.app.exercise.model.CourseWeekType;
import com.example.dailycarebe.app.exercise.model.Exercise;
import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.user.model.UserExerciseType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class ExerciseInitiate extends AbstractAuditingEntity {
    @Enumerated(EnumType.STRING)
    @Column
    private UserExerciseType userExerciseType;

    @Enumerated(EnumType.STRING)
    @Column
    private CourseWeekType courseWeekType;

    @Column
    private Integer courseWeek;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Exercise exercise;

}
