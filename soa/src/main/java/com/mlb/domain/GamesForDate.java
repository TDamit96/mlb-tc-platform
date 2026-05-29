package com.mlb.domain;

import java.time.LocalDate;
import java.util.List;

public class GamesForDate {
    public final LocalDate date;
    public final List<Game> games;

    public GamesForDate(LocalDate date, List<Game> games) {
        this.date = date;
        this.games = games;
    }
}