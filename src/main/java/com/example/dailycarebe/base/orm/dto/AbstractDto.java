package com.example.dailycarebe.base.orm.dto;

import com.example.dailycarebe.base.orm.model.HashIdSupport;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "uuid")
public abstract class AbstractDto implements HashIdSupport {
    private String uuid;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
