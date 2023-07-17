package com.example.dailycarebe.base.orm.dto;

import com.example.dailycarebe.base.orm.model.HashIdSupport;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
// 존재 이유는 ?
public abstract class AbstractEditDto implements HashIdSupport {
//    @NotNull
    private String uuid;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String changeLog;
}
