package pl.coderslab.model;

import pl.coderslab.util.BCrypt;
import pl.coderslab.util.DbUtil;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageUsers {

    public static void main(String[] args) {

        System.out.println("\nWitaj w aplikacji do edycji bazy użytkowników.\n\n" +
                "Napisz co chciałbyś zrobić:\n" +
                "  - \"dodaj\" żeby dodać nowego użytkownika,\n" +
                "  - \"edytuj\" żeby edytować istniejącego użytkownika,\n" +
                "  - \"usuń\" żeby usunąć istniejącego użytkownika,\n" +
                "  - \"zakończ\" żeby zakończyć działanie aplikacji.\n");

        Scanner scanner = new Scanner(System.in);
        String activity = scanner.nextLine();
        try (Connection connection = DbUtil.getConnection()) {

            while (!("dodaj".equalsIgnoreCase(activity) || "edytuj".equalsIgnoreCase(activity) || "usuń".equalsIgnoreCase(activity)
                    || "zakończ".equalsIgnoreCase(activity))) {
                System.out.print("Nie rozumiem. ");
                System.out.println("Napisz co chciałbyś zrobić:\n" +
                        "  - \"dodaj\" żeby dodać nowego użytkownika,\n" +
                        "  - \"edytuj\" żeby edytować istniejącego użytkownika,\n" +
                        "  - \"usuń\" żeby usunąć istniejącego użytkownika,\n" +
                        "  - \"zakończ\" żeby zakończyć działanie aplikacji.\n");
                activity = scanner.nextLine();
            }

            if ("dodaj".equalsIgnoreCase(activity)) {
                System.out.println();
                addUser(connection);
            } else if ("edytuj".equalsIgnoreCase(activity)) {
                System.out.println();
                editUser(connection);
            } else if ("usuń".equalsIgnoreCase(activity)) {
                System.out.println();
                deleteUser(connection);
            } else if ("zakończ".equalsIgnoreCase(activity)) {
                System.out.println();
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteUser(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id użytkownika którego chcesz usunąć:");
        int id = scanner.nextInt();
        User user = User.loadUserById(connection, id);
        user.delete(connection);
    }

    public static void editUser(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id użytkownika do edycji:");
        int id = scanner.nextInt();
        System.out.println("Podaj nowy username użytkownika:");
        String username = scanner.nextLine();
        System.out.println("Podaj nowy email użytkownika:");
        String email = scanner.nextLine();
        System.out.println("Podaj nowe hasło użytkownika:");
        String passwordCandidate = scanner.nextLine();
        String password = BCrypt.hashpw(passwordCandidate, BCrypt.gensalt());
        User user = User.loadUserById(connection, id);
        user.saveToDB(connection);
    }

    public static void addUser(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj username nowego użytkownika:");
        String username = scanner.nextLine();
        System.out.println("Podaj email nowego użytkownika:");
        String email = scanner.nextLine();
        System.out.println("Podaj hasło nowego użytkownika:");
        String passwordCandidate = scanner.nextLine();
        String password = BCrypt.hashpw(passwordCandidate, BCrypt.gensalt());
        User user = new User(username, email, password);
        user.saveToDB(connection);
    }
}
