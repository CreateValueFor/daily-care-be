package com.example.dailycarebe.user.dto;

import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class UserInfoViewDto extends AbstractAuditingDto {
    private Integer drinkingFrequency;

    private Integer isSmoking;

    private Integer workOutFrequency;

    private Integer constipationFrequency;

    private Integer diarrheaFrequency;

    private Integer bloodStoolFrequency;

    private Integer mucusStoolFrequency;

    private Integer bowelMovementsDayTimeFrequency;

    private Integer bowelMovementsNightTimeFrequency;

    private Integer rectalPainFrequency;

    private Integer gasBloatingFrequency;

    private Integer gasLeakingFrequency;

    private Integer stoolLeakingFrequency;

    private Integer anusPainFrequency;

    private Integer panicFrequency;

}
