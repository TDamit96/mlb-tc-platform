package com.mlb.domain;

/**
 * Represents a team's record; including wins, losses, and win percentage.
 * References a TeamRef object to identify the team.
 */
public class TeamRecord {
    public final TeamRef team;
    public final int wins;
    public final int losses;
    public final double winPct;

    public TeamRecord(TeamRef team, int wins, int losses) {
        this.team = team;
        this.wins = wins;
        this.losses = losses;
        this.winPct = wins + losses == 0 ? 0.0 : (double) wins / (wins + losses);
    }

    public TeamRef getTeamRef() {
        return team;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public double getWinPct() {
        return winPct;
    }
}
