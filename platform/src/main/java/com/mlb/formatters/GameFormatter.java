package com.mlb.formatters;

import com.mlb.domain.Game;
import java.time.format.DateTimeFormatter;

public class GameFormatter {
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String summarize(Game game) {
        if (game == null) return "(null game)";

        String away = game.awayTeam != null ? game.awayTeam.name : "Unknown Away";
        String home = game.homeTeam != null ? game.homeTeam.name : "Unknown Home";
        String venue = game.venueName != null ? game.venueName : "Unknown Venue";
        String date = game.gameDate != null ? game.gameDate.format(DATE_FORMAT) : "Unknown Date";

        return String.format("%-20s @ %-20s | %-25s | %s", away, home, venue, date);
    }
}
