package com.example.taskapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    TODO,
    IN_PROGRESS,
    DONE;

    @JsonCreator
    public static Status from(String value) {
        return Status.valueOf(value.toUpperCase());
    }
}
