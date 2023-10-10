package com.example.dailycarebe.app.movement.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class MovementDetail extends AbstractAuditingEntity {
    @ManyToOne
    private Movement movement;

    private LocalTime localTime;

    @Enumerated(EnumType.STRING)
    @Column
    private MovementType movementType;
}
