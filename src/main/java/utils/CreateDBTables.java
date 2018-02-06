package utils;


import db.DBConnectionProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
// for create data base tables
public class CreateDBTables {
    private DBConnectionProvider provider = DBConnectionProvider.getInstance();
    private Connection connection = provider.getConnection();
    private Statement statement = connection.createStatement();

    String name;
    String surname;
    String email;
    String password;
    String gender;
    String picture;
    String userStrQuery;
    String requestStrQuery;
    String friendStrQuery;
    String messageStrQuery;
    String adminAdd;
    private void loadProperties() throws IOException {
        final Properties properties = new Properties();
        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("dbConfig.properties");
        properties.load(inputStream);
        name=properties.getProperty("admin.name");
        surname=properties.getProperty("admin.surname");
        email=properties.getProperty("admin.email");
        password=properties.getProperty("admin.password");
        gender=properties.getProperty("admin.gender");
        picture=properties.getProperty("admin.picture");
    }
    private CreateDBTables() throws SQLException, IOException {
        loadProperties();
        str();
        statement.executeUpdate(userStrQuery);
        statement.executeUpdate(friendStrQuery);
        statement.executeUpdate(requestStrQuery);
        statement.executeUpdate(messageStrQuery);
        ResultSet resultSet = statement.executeQuery("select `email` from `user` where `email`='"+email+"'");

        if (!resultSet.next()) {
            statement.executeUpdate(adminAdd);
        }

    }
    private void str(){

           userStrQuery =
                " CREATE TABLE IF NOT EXISTS `user` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` varchar(255) NOT NULL,\n" +
                        "  `surname` varchar(255) DEFAULT NULL,\n" +
                        "  `email` varchar(255) NOT NULL,\n" +
                        "  `password` varchar(255) NOT NULL,\n" +
                        "  `gender` enum('MALE','FEMALE') NOT NULL,\n" +
                        "  `status` enum('ONLINE','OFFLINE') NOT NULL,\n" +
                        "  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                        "  `picture` varchar(255) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8\n";
          adminAdd = "INSERT INTO `user` (`name`, `surname`, `email`, `password`, `gender`,`picture`,`status`)" +
                " VALUES ('"+name+"', '"+surname+"', '"+email+"', '"+password+"','"
                  +gender+"','"+picture+"','ONLINE' );";
          friendStrQuery =
                "CREATE TABLE IF NOT EXISTS `friend` (\n" +
                        "  `user_id` int(11) NOT NULL,\n" +
                        "  `friend_id` int(11) NOT NULL,\n" +
                        "  PRIMARY KEY (`user_id`,`friend_id`),\n" +
                        "  KEY `friend_id` (`friend_id`),\n" +
                        "  CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,\n" +
                        "  CONSTRAINT `friend_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`) ON DELETE CASCADE\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8\n";
           requestStrQuery =
                "CREATE TABLE IF NOT EXISTS `request` (\n" +
                        "  `from_id` int(11) NOT NULL,\n" +
                        "  `to_id` int(11) NOT NULL,\n" +
                        "  PRIMARY KEY (`from_id`,`to_id`),\n" +
                        "  KEY `to_id` (`to_id`),\n" +
                        "  CONSTRAINT `request_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,\n" +
                        "  CONSTRAINT `request_ibfk_2` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`) ON DELETE CASCADE\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8\n";

           messageStrQuery =
                "CREATE TABLE IF NOT EXISTS `message` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `title` varchar(255) DEFAULT NULL,\n" +
                        "  `message` text NOT NULL,\n" +
                        "  `from_id` int(11) NOT NULL,\n" +
                        "  `to_id` int(11) NOT NULL,\n" +
                        "  `status` enum('NEW','OLD') NOT NULL,\n" +
                        "  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `from_id` (`from_id`),\n" +
                        "  KEY `to_id` (`to_id`),\n" +
                        "  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,\n" +
                        "  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`) ON DELETE CASCADE\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8\n";

    }

    public static void getInstance() throws SQLException, IOException {
        new CreateDBTables();
    }

}
