package com.mlb.client;

import com.mlb.services.standings.StandingsService;
import com.mlb.services.standings.impl.StandingsServiceImpl;

/**
 * Console client for interacting with the MLB standings service.
 */
public class StandingsClient {
    public static void main(String[] args) throws Exception {
        StandingsService service = new StandingsServiceImpl();
        var standings = service.getLeagueStandings();

        System.out.println("Divisions: " + standings.getDivisions().size());
        standings.getDivisions().forEach(div -> {
            System.out.println("Division: " + div.getName());
            div.getTeams().forEach(team -> {
                System.out.println("  " + team.getTeamRef().getName() +
                                   " (" + team.getWins() + "-" + team.getLosses() + ")");
            });
        });
    }
}
