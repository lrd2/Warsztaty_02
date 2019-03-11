package pl.coderslab.model;

import pl.coderslab.util.DbUtil;
import pl.coderslab.util.TimeConverters;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AssignExercise {

    public static void main(String[] args) {
        System.out.println("\nWitaj w aplikacji do przypiswania ćwiczeń.\n\n" +
                "Napisz co chciałbyś zrobić:\n" +
                "  - \"dodaj\" żeby przypisać ćwiczenie do użytkownika,\n" +
                "  - \"wyświetl\" żeby przejrzeć rozwiązania wybranego użytkownika,\n" +
                "  - \"zakończ\" żeby zakończyć działanie aplikacji.\n");

        Scanner scanner = new Scanner(System.in);
        String activity = scanner.nextLine();

        try (Connection connection = DbUtil.getConnection()) {

            while (!("dodaj".equalsIgnoreCase(activity) || "wyświetl".equalsIgnoreCase(activity) || "zakończ".equalsIgnoreCase(activity))) {
                System.out.print("Nie rozumiem. ");
                System.out.println("Napisz co chciałbyś zrobić:\n" +
                        "  - \"dodaj\" żeby przypisać ćwiczenie do użytkownika,\n" +
                        "  - \"wyświetl\" żeby przejrzeć rozwiązania wybranego użytkownika,\n" +
                        "  - \"zakończ\" żeby zakończyć działanie aplikacji.\n");
                activity = scanner.nextLine();
            }

            if ("dodaj".equalsIgnoreCase(activity)) {
                System.out.println("Lista użytkowników:");
                printAllUsers(connection);
                System.out.println("Któremu użytkownikowi chcesz przypisać zadanie? Podaj id: ");
                int selectedUserId = scanner.nextInt();
                System.out.println("Wybrano użytkownika o id = " + selectedUserId + ". Zadania do których można przypisać użytkownika: ");
                printAllExercises(connection);
                System.out.println("Do którego zadania chcesz przypisać użytkownika o id = " + selectedUserId + "? Podaj id zadania: ");
                int selectedExerciseId = scanner.nextInt();
                assignExerciseToUser(connection, selectedUserId, selectedExerciseId);

            } else if ("wyświetl".equalsIgnoreCase(activity)) {
                System.out.println("Lista użytkowników:");
                printAllUsers(connection);
                System.out.println("Podaj id użytkownika, którego rozwiązania chcesz wyświetlić: ");
                int selectedUserId = scanner.nextInt();
                printSolutionById(connection, selectedUserId);

            } else if ("zakończ".equalsIgnoreCase(activity)) {
                System.out.println();
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void assignExerciseToUser(Connection connection, int selectedUserId, int selectedExerciseId) throws SQLException {
        Solution solution = new Solution();
        solution.setCreated(TimeConverters.getCurrentTimestamp());
        solution.setExercise_id(selectedExerciseId);
        solution.setUsers_id(selectedUserId);
        solution.saveToDB(connection);
    }

    private static void printAllExercises(Connection connection) throws SQLException {
        String query2 = "select * from exercise";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        while (resultSet2.next()) {
            int exerciseId = resultSet2.getInt("id");
            String exerciseTitle = resultSet2.getString("title");
            String exerciseDescription = resultSet2.getString("description");
            System.out.println("Zadanie o id: " + exerciseId + " | " + exerciseTitle + " | " + exerciseDescription);
        }
    }

    private static void printAllUsers(Connection connection) throws SQLException {
        String query = "select * from users";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int userId = resultSet.getInt("id");
            String username = resultSet.getString("username");
            System.out.println("Użytkownik o id: " + userId + " | " + username);
        }
    }

    private static void printSolutionById(Connection connection, int selectedUserId) throws SQLException {
        String query = "select * from solution where users_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, selectedUserId);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Lista rozwiązań użytkownika o id = " + selectedUserId + " jest następująca:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String created = resultSet.getString("created");
            String updated = resultSet.getString("updated");
            String description = resultSet.getString("description");
            String exercise_id = resultSet.getString("exercise_id");
            String users_id = resultSet.getString("users_id");
            System.out.println(id + " | " + created + " | " + updated + " | " + description + " | " + exercise_id + " | " + users_id);
        }
    }
}

