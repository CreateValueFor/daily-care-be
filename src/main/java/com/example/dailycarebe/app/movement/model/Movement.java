package com.example.dailycarebe.app.movement.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.user.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Movement extends AbstractAuditingEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private LocalDate localDate;

    @OneToMany(mappedBy = "movement", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<MovementDetail> movementDetails;
}
