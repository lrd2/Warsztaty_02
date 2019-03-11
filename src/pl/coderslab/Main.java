package pl.coderslab;

import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;
import pl.coderslab.util.TimeConverters;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution(TimeConverters.getTimestamp("2018-03-25 06:30:35"), TimeConverters.getTimestamp("2018-03-25 06:45:20"), "Opis", 1, 2 );

        try (Connection connection = DbUtil.getConnection()) {
//            Solution[] solutionArray = Solution.loadAllSolutions(connection);
//            System.out.println(Arrays.toString(solutionArray));


//            user.saveToDB(connection);
//            user = User.loadUserById(connection,1);
//            user.setUsername("Pankracy");
//            user.saveToDB(connection);
//            User[] users = user.loadAllUsers(connection);
//            System.out.println(Arrays.toString(users));
//            user.delete(connection);
//            System.out.println(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
