package com.example.dailycarebe.user.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
public class UserInfo extends AbstractAuditingEntity {
    @Column
    private Integer drinkingFrequency;

    @Column
    private Integer isSmoking;

    @Column
    private Integer workOutFrequency;

    @Column
    private Integer constipationFrequency;

    @Column
    private Integer diarrheaFrequency;

    @Column
    private Integer bloodStoolFrequency;

    @Column
    private Integer mucusStoolFrequency;

    @Column
    private Integer bowelMovementsDayTimeFrequency;

    @Column
    private Integer bowelMovementsNightTimeFrequency;

    @Column
    private Integer rectalPainFrequency;

    @Column
    private Integer gasBloatingFrequency;

    @Column
    private Integer gasLeakingFrequency;

    @Column
    private Integer stoolLeakingFrequency;

    @Column
    private Integer anusPainFrequency;

    @Column
    private Integer panicFrequency;

}
