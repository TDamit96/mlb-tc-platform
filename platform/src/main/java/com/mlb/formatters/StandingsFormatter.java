package com.mlb.formatters;

import com.mlb.domain.*;

/**
 * Utility class for formatting Standings objects into human-readable strings.
 */
public class StandingsFormatter {

    public static void print(Standings standings) {
        for (Division div : standings.divisions) {
            System.out.println("\n=== " + div.name + " ===");
            for (TeamRecord t : div.teams) {
                System.out.printf("%-20s  %3d-%-3d  (%.3f)\n",
                        t.team.name, t.wins, t.losses, t.winPct);
            }
        }
    }
}