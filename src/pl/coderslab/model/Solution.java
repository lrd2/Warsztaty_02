package pl.coderslab.model;

import pl.coderslab.util.BCrypt;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Timestamp;


public class Solution {

    private int id;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private int exercise_id;
    private int users_id;

    public Solution() {
    }

    public Solution(Timestamp created, Timestamp updated, String description, int exercise_id, int users_id) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise_id = exercise_id;
        this.users_id = users_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(created, updated, description, exercise_id, users_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement
                    = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, this.created);
            preparedStatement.setTimestamp(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exercise_id);
            preparedStatement.setInt(5, this.users_id);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET created=?, updated=?, description=?, exercise_id=?, users_id=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, this.created);
            preparedStatement.setTimestamp(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exercise_id);
            preparedStatement.setInt(5, this.users_id);
            preparedStatement.setInt(6, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                ", exercise_id='" + exercise_id + '\'' +
                ", users_id='" + users_id + '\'' +
                '}';
    }

    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.users_id = resultSet.getInt("users_id");
            return loadedSolution;
        }
        return null;
    }

    static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.users_id = resultSet.getInt("users_id");
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    static public Solution[] loadAllByUserId(Connection conn, int id) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution where users_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.users_id = resultSet.getInt("users_id");
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }
    static public Solution[] loadAllByExerciseId(Connection conn, int id) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution where exercise_id = ? order by updated DESC";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.users_id = resultSet.getInt("users_id");
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }
}
