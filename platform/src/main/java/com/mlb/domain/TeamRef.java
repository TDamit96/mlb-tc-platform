package com.mlb.domain;

/**
 * Represents an reference to a MLB team, containing the team's ID and name.
 */
public class TeamRef {
    public final int id;
    public final String name;

    public TeamRef(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}