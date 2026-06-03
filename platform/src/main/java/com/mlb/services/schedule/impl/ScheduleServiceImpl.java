package com.mlb.services.schedule.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlb.domain.*;
import com.mlb.bridge.ItkBridge;
import com.mlb.bridge.ScheduleBridge;
import com.mlb.services.schedule.ScheduleException;
import com.mlb.services.schedule.ScheduleService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ScheduleService interface, responsible for fetching and parsing MLB schedule data.
 * It uses a native bridge (ScheduleBridge) to retrieve JSON data from the MLB API, and then parses it into domain models.
 */
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleBridge bridge = new ScheduleBridge();
    private final ObjectMapper mapper = new ObjectMapper();

    // fetches schedule data for a specific date and parses it into a GamesForDate object
    @Override
    public GamesForDate getGamesForDate(LocalDate date) throws ScheduleException {
        String urlDate = date.toString();
        ItkBridge.ItkResult result = bridge.getScheduleForDate(urlDate);

        if (result.errorCode != 0) {
            throw new ScheduleException("Native error code: " + result.errorCode);
        }
        if (result.json == null) {
            throw new ScheduleException("Native layer returned null JSON");
        }

		// attempts to parse json response
        try {
            return parseGamesForDateJson(result.json);
        } catch (Exception e) {
			e.printStackTrace();
            throw new ScheduleException("Failed to parse schedule JSON", e);
        }
    }

    // fetches schedule data for a date range by calling getGamesForDate for each date and aggregating results
    @Override
    public List<Game> getGamesForDateRange(LocalDate startDate, LocalDate endDate) throws Exception {
        List<Game> allGames = new ArrayList<>();
        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {
            GamesForDate gamesForDate = getGamesForDate(date);
            allGames.addAll(gamesForDate.games);
            date = date.plusDays(1);
        }

        return allGames;
    }

    // filters games for a specific team on a given date by calling getGamesForDate and then filtering the results
    @Override
    public List<Game> getGamesForTeam(String teamName, LocalDate date) throws Exception {
        GamesForDate gamesForDate = getGamesForDate(date);
        return gamesForDate.games.stream()
            .filter(g -> g.homeTeam.name.equalsIgnoreCase(teamName)
                    || g.awayTeam.name.equalsIgnoreCase(teamName))
            .toList();
    }

    // helper method to parse the JSON response from the MLB API into a GamesForDate object
    private GamesForDate parseGamesForDateJson(String json) throws Exception {
        JsonNode root = mapper.readTree(json);

        JsonNode dateNode = root.get("dates").get(0);
        LocalDate date = LocalDate.parse(dateNode.get("date").asText());

        List<Game> games = new ArrayList<>();

        for (JsonNode gameNode : dateNode.get("games")) {
            long gamePk = gameNode.get("gamePk").asLong();
            ZonedDateTime gameDate = ZonedDateTime.parse(gameNode.get("gameDate").asText());

            JsonNode homeTeamNode = gameNode.get("teams").get("home").get("team");
            JsonNode awayTeamNode = gameNode.get("teams").get("away").get("team");

            TeamRef home = new TeamRef(
                homeTeamNode.get("id").asInt(),
                homeTeamNode.get("name").asText()
            );

            TeamRef away = new TeamRef(
                awayTeamNode.get("id").asInt(),
                awayTeamNode.get("name").asText()
            );

            String venueName = gameNode.get("venue").get("name").asText();

            games.add(new Game(gamePk, home, away, gameDate, venueName));
        }

        return new GamesForDate(date, games);
    }
}