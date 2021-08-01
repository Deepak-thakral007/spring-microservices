package com.thoughtmechanix.organization.utils;

import com.thoughtmechanix.organization.model.DailyPrice;

import java.util.ArrayList;
import java.util.List;

public class BatchPersister {

    private final int batchSize;

    private List<DailyPrice>  dailyPrices =  new ArrayList<>();

    public BatchPersister(int batchSize) {
        this.batchSize = batchSize;
    }

    public DailyPrice addObject(DailyPrice dailyPrice)
    {
        dailyPrices.add(dailyPrice);
        return dailyPrice;
    }


}
