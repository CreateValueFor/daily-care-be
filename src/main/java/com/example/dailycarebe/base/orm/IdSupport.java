package com.example.dailycarebe.base.orm;


import com.example.dailycarebe.util.HashidsUtil;

import java.io.Serializable;

public interface IdSupport extends Serializable {
    Long getId();
    void setId(Long id);

    default String getIdHash() {
        return HashidsUtil.encodeNumber(getId());
    }

    default void setIdHash(String idHash) {
        long decodedNumber = HashidsUtil.decodeNumber(idHash);
        if (decodedNumber >= 0) {
            setId(decodedNumber);
        }
    }
}
