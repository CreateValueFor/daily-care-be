package com.example.dailycarebe.app.exercise.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.user.model.User;
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
public class ExerciseAdditional extends AbstractAuditingEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exercise exercise;

    @Column
    private Integer courseWeek;

    @Column
    private Integer courseDay;

    @Enumerated(EnumType.STRING)
    @Column
    private CourseType courseType;
}
