package com.mlb.services.schedule;

/**
 * Custom exception class for handling errors related to fetching and parsing MLB schedule data.
 */
public class ScheduleException extends Exception {
    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(String message, Throwable cause) {
        super(message, cause);
    }
}