package com.mlb.services.schedule;

import com.mlb.domain.Game;
import com.mlb.domain.GamesForDate;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    GamesForDate getGamesForDate(LocalDate date) throws ScheduleException;
    List<Game> getGamesForTeam(String teamName, LocalDate date) throws Exception;
    List<Game> getGamesForDateRange(LocalDate start, LocalDate end) throws Exception;
}