package com.thoughtmechanix.organization.utils.kafka;

public class ParsingResult {

    public boolean isSuccess() {
        return success;
    }

    public Integer getNoOfRecords() {
        return noOfRecords;
    }

    private final boolean success;
    private final Integer noOfRecords;
    private final Exception exception;

    public ParsingResult(boolean success, Integer noOfRecords, Exception exception) {

        this.success = success;
        this.noOfRecords = noOfRecords;
        this.exception = exception;
    }
}
