package com.mlb.client;

import com.mlb.domain.Game;
import com.mlb.domain.GamesForDate;
import com.mlb.services.schedule.ScheduleService;
import com.mlb.services.schedule.impl.ScheduleServiceImpl;

import java.time.LocalDate;

public class ScheduleClient {

    public static void main(String[] args) throws Exception {
		
		System.out.println("ScheduleClient is running...");

        ScheduleService service = new ScheduleServiceImpl();

        // You can change this to any date you want
        LocalDate date = LocalDate.now();

        GamesForDate day = service.getGamesForDate(date);

        System.out.println("Games on " + day.date + ":");
        for (Game g : day.games) {
            System.out.println(
                g.awayTeam.name + " @ " +
                g.homeTeam.name + " — " +
                g.venueName + " — " +
                g.gameDate
            );
        }
    }
}