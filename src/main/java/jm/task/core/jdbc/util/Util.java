package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hibernate.cfg.AvailableSettings.DRIVER;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER= "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mydbTest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root000000!";


    private static  Connection connection;

    private Util() {
    }

    public static Connection getConnection(){

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL,USERNAME, PASSWORD);
            System.out.println("connect Ok");

        } catch (ClassNotFoundException e) {
            System.out.println("connect class Util");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("connect class Util");
            throw new RuntimeException(e);
        }
        return connection;
    }



}