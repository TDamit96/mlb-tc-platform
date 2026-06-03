package com.mlb.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a list of Game objects (games) scheduled for a specific date.
 */
public class GamesForDate {
    public final LocalDate date;
    public final List<Game> games;

    public GamesForDate(LocalDate date, List<Game> games) {
        this.date = date;
        this.games = games;
    }
}