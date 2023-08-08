package com.example.dailycarebe.app.exercise.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class CourseMap extends AbstractAuditingEntity {
    @Column
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @Column
    @Enumerated(EnumType.STRING)
    private CourseWeekType courseWeekType;

    @Column
    private Integer courseDay;
}
