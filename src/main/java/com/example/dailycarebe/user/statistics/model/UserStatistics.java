package com.example.dailycarebe.user.statistics.model;

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
public class UserStatistics extends AbstractAuditingEntity {
    @Column
    private Integer age;

    @Column
    private Double height;

    @Column
    private Double weight;
}
