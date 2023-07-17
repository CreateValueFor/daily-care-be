package com.example.dailycarebe.user.auth.authorization.model;

import com.example.dailycarebe.auth.authorization.model.AppRole;
import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
@EqualsAndHashCode(callSuper = false, of = {"user", "appRole"})
@ToString(exclude = {"user", "appRole"})
@AllArgsConstructor
@NoArgsConstructor
public class UserAppRole extends AbstractAuditingEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private AppRole appRole;
}
