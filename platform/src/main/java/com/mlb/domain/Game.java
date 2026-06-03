package com.mlb.domain;

import java.time.ZonedDateTime;

/**
 * Represents a game in the MLB schedule.
 */
public class Game {
    public final long gamePk;
    public final TeamRef homeTeam;
    public final TeamRef awayTeam;
    public final ZonedDateTime gameDate;
    public final String venueName;

    public Game(long gamePk,
                TeamRef homeTeam,
                TeamRef awayTeam,
                ZonedDateTime gameDate,
                String venueName) {
        this.gamePk = gamePk;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameDate = gameDate;
        this.venueName = venueName;
    }
}