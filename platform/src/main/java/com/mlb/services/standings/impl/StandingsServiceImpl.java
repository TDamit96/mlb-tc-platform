package com.mlb.services.standings.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlb.domain.*;
import com.mlb.services.standings.StandingsException;
import com.mlb.services.standings.StandingsService;
import com.mlb.bridge.ItkBridge;
import com.mlb.bridge.StandingsBridge;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the StandingsService interface, responsible for fetching and parsing MLB standings data.
 * It uses a native bridge (StandingsBridge) to retrieve JSON data from the MLB API, and then parses it into domain models.
 */
public class StandingsServiceImpl implements StandingsService {

    private final StandingsBridge bridge = new StandingsBridge();
    private final ObjectMapper mapper = new ObjectMapper();

    // fetches league standings data from the native bridge and parses it into a Standings object
    @Override
    public Standings getLeagueStandings() throws Exception {
        ItkBridge.ItkResult result = bridge.getStandingsJson();

        if (result.errorCode != 0) {
            throw new StandingsException("Native error code: " + result.errorCode);
        }
        if (result.json == null) {
            throw new StandingsException("Native layer returned null JSON");
        }

        try {
            return parseStandingsJson(result.json);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StandingsException("Failed to parse standings JSON", e);
        }
    }

    // helper method to parse the JSON response from the MLB API into a Standings object
    private Standings parseStandingsJson(String json) throws Exception {
        JsonNode root = mapper.readTree(json);
        JsonNode recordsNode = root.get("records");

        List<Division> divisions = new ArrayList<>();

        if (recordsNode != null && recordsNode.isArray()) {
            for (JsonNode recordNode : recordsNode) {
                // Division name
                String name = recordNode.path("division").path("name").asText();

                // Team records
                List<TeamRecord> teams = new ArrayList<>();
                JsonNode teamRecordsNode = recordNode.path("teamRecords");

                if (teamRecordsNode != null && teamRecordsNode.isArray()) {
                    for (JsonNode teamNode : teamRecordsNode) {
                        JsonNode teamInfo = teamNode.path("team");

                        TeamRef ref = new TeamRef(
                            teamInfo.path("id").asInt(),
                            teamInfo.path("name").asText()
                        );

                        int wins = teamNode.path("wins").asInt();
                        int losses = teamNode.path("losses").asInt();

                        teams.add(new TeamRecord(ref, wins, losses));
                    }
                }

                divisions.add(new Division(name, teams));
            }
        }

        return new Standings(divisions);
    }
}
