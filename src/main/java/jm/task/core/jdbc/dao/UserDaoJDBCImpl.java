package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTableCommand = "CREATE TABLE User (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(50), lastName VARCHAR(50), age INT NOT NULL)";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(createTableCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String deleteTableCommand = "DROP TABLE IF EXISTS user";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(deleteTableCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserCommand = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(saveUserCommand)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUserByIdCommand = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(removeUserByIdCommand)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAllUsersCommand = "SELECT * FROM user";

        try (Statement statement = Util.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(getAllUsersCommand)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersTableCommand = "DELETE FROM user";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(cleanUsersTableCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
