package com.example.dailycarebe.app.exercise.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class CourseMap extends AbstractAuditingEntity {
    @Column
    private CourseType courseType;

    @Column
    private CourseWeekType courseWeekType;

    @Column
    private Integer courseDay;
}
