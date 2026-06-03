package com.mlb.services.standings;

import com.mlb.domain.Standings;

/**
 * Interface for the StandingsService, which provides a method to fetch the current MLB standings.
 */
public interface StandingsService {
    Standings getLeagueStandings() throws Exception;
}
