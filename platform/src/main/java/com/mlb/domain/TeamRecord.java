package com.mlb.domain;

// import com.mlb.domain.TeamRef;

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
