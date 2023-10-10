package com.example.dailycarebe.app.exercise.tmp.model;

import com.example.dailycarebe.app.exercise.model.ExerciseEvaluationType;
import com.example.dailycarebe.app.exercise.record.model.ExerciseRecord;
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
public class ExerciseTmp extends AbstractAuditingEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private ExerciseRecord exerciseRecord;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Column
    private LocalDate today;

    @Enumerated(EnumType.STRING)
    @Column
    private ExerciseEvaluationType exerciseEvaluationType;

    @Column(columnDefinition = "BIT DEFAULT 0")
    private Boolean complete;
}
