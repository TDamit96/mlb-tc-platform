package com.mlb.domain;

import java.util.List;

/**
 * Represents a division in the MLB standings, containing a list of TeamRecord objects.
 */
public class Division {
    public final String name;
    public final List<TeamRecord> teams;

    public Division(String name, List<TeamRecord> teams) {
        this.name = name;
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public List<TeamRecord> getTeams() {
        return teams;
    }
}
