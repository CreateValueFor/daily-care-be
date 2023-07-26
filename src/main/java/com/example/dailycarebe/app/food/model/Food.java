package com.example.dailycarebe.app.food.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Food extends AbstractAuditingEntity {
}
