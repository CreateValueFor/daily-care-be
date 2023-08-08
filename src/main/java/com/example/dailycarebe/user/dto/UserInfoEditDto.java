package com.example.dailycarebe.user.dto;

import com.example.dailycarebe.base.orm.dto.AbstractEditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoEditDto extends AbstractEditDto {
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
