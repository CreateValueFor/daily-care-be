package com.example.dailycarebe.app.food.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Food extends AbstractAuditingEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Column
    private String subject;

    @Column
    private LocalDateTime startTime;
}
