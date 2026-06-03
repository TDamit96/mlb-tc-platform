package com.mlb.services.standings;

import com.mlb.domain.Standings;

public interface StandingsService {
    Standings getLeagueStandings() throws Exception;
}
