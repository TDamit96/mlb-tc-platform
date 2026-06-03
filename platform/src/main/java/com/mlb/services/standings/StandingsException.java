package com.mlb.services.standings;

/**
 * Custom exception class for handling errors related to fetching and parsing MLB standings data.
 */
public class StandingsException extends Exception {
    public StandingsException(String message) {
        super(message);
    }

    public StandingsException(String message, Throwable cause) {
        super(message, cause);
    }
}
