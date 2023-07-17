package com.example.dailycarebe.base.orm.model;

import java.io.Serializable;

public interface HashIdSupport extends Serializable {
    String getUuid();

    void setUuid(String idHash);
}
