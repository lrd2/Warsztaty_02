package pl.coderslab.model;

import pl.coderslab.util.BCrypt;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageExercises {

    public static void main(String[] args) {

        System.out.println("\nWitaj w aplikacji do edycji bazy ćwiczeń.\n\n" +
                "Napisz co chciałbyś zrobić:\n" +
                "  - \"dodaj\" żeby dodać nowe ćwiczenie,\n" +
                "  - \"edytuj\" żeby edytować istniejące ćwiczenie,\n" +
                "  - \"usuń\" żeby usunąć istniejące ćwiczenie,\n" +
                "  - \"zakończ\" żeby zakończyć działanie aplikacji.\n");

        Scanner scanner = new Scanner(System.in);
        String activity = scanner.nextLine();
        try (Connection connection = DbUtil.getConnection()) {

            while (!("dodaj".equalsIgnoreCase(activity) || "edytuj".equalsIgnoreCase(activity) || "usuń".equalsIgnoreCase(activity)
                    || "zakończ".equalsIgnoreCase(activity))) {
                System.out.print("Nie rozumiem. ");
                System.out.println("Napisz co chciałbyś zrobić:\n" +
                        "  - \"dodaj\" żeby dodać nowe ćwiczenie,\n" +
                        "  - \"edytuj\" żeby edytować istniejące ćwiczenie,\n" +
                        "  - \"usuń\" żeby usunąć istniejące ćwiczenie,\n" +
                        "  - \"zakończ\" żeby zakończyć działanie aplikacji.\n");
                activity = scanner.nextLine();
            }

            if ("dodaj".equalsIgnoreCase(activity)) {
                System.out.println();
                addExercise(connection);
            } else if ("edytuj".equalsIgnoreCase(activity)) {
                System.out.println();
                editExercise(connection);
            } else if ("usuń".equalsIgnoreCase(activity)) {
                System.out.println();
                deleteExercise(connection);
            } else if ("zakończ".equalsIgnoreCase(activity)) {
                System.out.println();
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteExercise(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id ćwiczenia które chcesz usunąć:");
        int id = scanner.nextInt();
        Exercise exercise = Exercise.loadExerciseById(connection, id);
        exercise.delete(connection);
    }

    public static void editExercise(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id ćwiczenia do edycji:");
        int id = scanner.nextInt();
        System.out.println("Podaj nowy tytuł ćwiczenia:");
        String title = scanner.nextLine();
        System.out.println("Podaj nowy opis świczenia:");
        String description = scanner.nextLine();
        Exercise exercise = Exercise.loadExerciseById(connection, id);
        exercise.saveToDB(connection);
    }

    public static void addExercise(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj tytuł nowego ćwiczenia:");
        String title = scanner.nextLine();
        System.out.println("Podaj opis nowego świczenia:");
        String description = scanner.nextLine();
        Exercise exercise = new Exercise(title, description);
        exercise.saveToDB(connection);
    }
}
