package com.example.dailycarebe.user.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class UserPain extends AbstractAuditingEntity {
    private UserPainPart userPainPart;

    private Integer pain;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
