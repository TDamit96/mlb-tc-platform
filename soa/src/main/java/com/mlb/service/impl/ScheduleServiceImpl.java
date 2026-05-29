package com.mlb.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlb.domain.*;
import com.mlb.itk.ItkBridge;
import com.mlb.service.ScheduleException;
import com.mlb.service.ScheduleService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleServiceImpl implements ScheduleService {

    private final ItkBridge bridge = new ItkBridge();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public GamesForDate getGamesForDate(LocalDate date) throws ScheduleException {
        String url = "https://statsapi.mlb.com/api/v1/schedule?sportId=1&date=" + date;
        ItkBridge.ItkResult result = bridge.fetchUrl(url);

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

    // parses JSON into domain model
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