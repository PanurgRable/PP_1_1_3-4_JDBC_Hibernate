package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection;

    public UserDaoJDBCImpl() {
        connection = getConnection();
    }

    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS Abc (" + // НАЗВАНИЕ ТАБЛИЦЫ
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(45) , " +
                "lastName VARCHAR(45), " +
                "age INT )";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute(sql);
            connection.commit();
            System.out.println("Создана таблица. Метод createUsersTable");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }



    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Abc";   // НАЗВАНИЕ ТАБЛИЦЫ
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute(sql);
            connection.commit();


            System.out.println("Удалена таблица. метод dropUsersTable ");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO Abc (name, lastName, age) VALUES (?, ?, ?)"; // НАЗВАНИЕ ТАБЛИЦЫ
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Abc WHERE Id = ?"; // НАЗВАНИЕ ТАБЛИЦЫ
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("удален пользователь " + id + "метод removeUserById");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Abc"; // НАЗВАНИЕ ТАБЛИЦЫ
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user1 = new User();
                user1.setName(resultSet.getString("name"));
                user1.setLastName(resultSet.getString("lastName"));
                user1.setAge(resultSet.getByte("age"));
                userList.add(user1);
                System.out.println(userList);
                System.out.println("Вывод списка метод getAllUsers");

            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM Abc"; // НАЗВАНИЕ ТАБЛИЦЫ
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.execute();
            connection.commit();
            System.out.println("Удален юзер метод cleanUsersTable");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}