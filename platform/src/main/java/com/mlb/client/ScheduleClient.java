package com.mlb.client;

import com.mlb.domain.Game;
import com.mlb.domain.GamesForDate;
import com.mlb.services.schedule.ScheduleService;
import com.mlb.services.schedule.impl.ScheduleServiceImpl;
import com.mlb.formatters.GameFormatter;

import java.time.LocalDate;
import java.util.Scanner;

public class ScheduleClient {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        ScheduleService service = new ScheduleServiceImpl();

        while (true) {
            System.out.println("\n=== MLB Schedule Console ===");
            System.out.println("1. Show today's games");
            System.out.println("2. Show games for a specific date");
            System.out.println("3. Filter games by team");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> showGames(service, LocalDate.now());
                case 2 -> {
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    showGames(service, date);
                }
                case 3 -> {
                    System.out.print("Enter team name: ");
                    String team = scanner.nextLine();
                    filterByTeam(service, team);
                }
                case 4 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void showGames(ScheduleService service, LocalDate date) throws Exception {
        GamesForDate games = service.getGamesForDate(date);
        games.games.forEach(g -> System.out.println(GameFormatter.summarize(g)));
    }

    private static void filterByTeam(ScheduleService service, String team) throws Exception {
        GamesForDate games = service.getGamesForDate(LocalDate.now());
        games.games.stream()
            .filter(g -> g.homeTeam.name.equalsIgnoreCase(team) ||
                         g.awayTeam.name.equalsIgnoreCase(team))
            .forEach(g -> System.out.println(GameFormatter.summarize(g)));
    }
}