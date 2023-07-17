package com.example.dailycarebe.base.paging.dto;

import lombok.Data;

import javax.annotation.Nonnull;
import java.util.List;

@Data
public abstract class BasePaginationResultDto<DATA> {
    private String cursor;

    private long totalCount;

    private long page;

    private List<DATA> data;

    public long getTotalCount() {
        return totalCount;
    }

    @Nonnull
    public List<DATA> getData() {
        return data;
    }

    public long getPage() {
        return page;
    }
}
