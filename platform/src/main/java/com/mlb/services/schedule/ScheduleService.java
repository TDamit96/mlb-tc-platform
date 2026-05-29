package com.mlb.services.schedule;

import com.mlb.domain.GamesForDate;

import java.time.LocalDate;

public interface ScheduleService {
    GamesForDate getGamesForDate(LocalDate date) throws ScheduleException;
}