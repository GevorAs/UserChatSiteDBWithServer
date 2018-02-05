package manager;

import db.DBConnectionProvider;
import model.Gender;
import model.User;
import model.UserStatus;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
    private final Connection connection;

    public UserManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void add(User user) throws SQLException {
        String query = "insert into `user`(name,surname,email,password,gender,status,picture)" +
                "values(?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getGender().name());
        preparedStatement.setString(6, user.getStatus().name());
        preparedStatement.setString(7, user.getPicture());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            user.setId(resultSet.getInt(1));
        }

    }

    public void update(User user) throws SQLException {
        String query = "update `user` Set `name`=?, `surname`=?," +
                " `email`=?, `password`=?, `status`=?,`picture`=? where id=? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getStatus().name());
        preparedStatement.setString(6, user.getPicture());
        preparedStatement.setLong(7, user.getId());
        preparedStatement.executeUpdate();

    }

    public User getUserByEmailAndPassword(String email, String password) throws SQLException {
        String query = "select * from user where email=? and password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setGender(Gender.valueOf(resultSet.getString("gender")));
            user.setStatus(UserStatus.valueOf(resultSet.getString("status")));
            user.setPicture(resultSet.getString("picture"));
            user.setTimestamp(resultSet.getString("timestamp"));
        }
        return user;

    }

    public boolean isEmailExist(String email) throws SQLException {
        String query = "select * from user where email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return true;
        }else return false;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new LinkedList<User>();
        String query = "select *from user";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setGender(Gender.valueOf(resultSet.getString("gender")));
            user.setStatus(UserStatus.valueOf(resultSet.getString("status")));
            user.setPicture(resultSet.getString("picture"));
            user.setTimestamp(resultSet.getString("timestamp"));
            users.add(user);
        }

        return users;
    }

    public void addFriend(long userId, long friendId) throws SQLException {
        String query = "insert into friend(user_id,friend_id)values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, friendId);
        preparedStatement.executeUpdate();

    }

    public void addRequest(long fromId, long toId) throws SQLException {
        String query = "insert into request(from_id,to_id)values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, fromId);
        preparedStatement.setLong(2, toId);
        preparedStatement.executeUpdate();
    }

    public void removeRequest(long fromId, long toId) throws SQLException {
        String query = "delete from request where (from_id=? and to_id=?) or (from_id=? and to_id=?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, fromId);
        preparedStatement.setLong(2, toId);
        preparedStatement.setLong(3, toId);
        preparedStatement.setLong(4, fromId);
        preparedStatement.executeUpdate();
    }

    public void removeFriend(long userId, long friendId) throws SQLException {
        String query = "delete from friend where (user_id=? and friend_id=?)or" +
                "(user_id=? and friend_id=?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, friendId);
        preparedStatement.setLong(3, friendId);
        preparedStatement.setLong(4, userId);
        preparedStatement.executeUpdate();
    }

    public User getUserById(long id) throws SQLException {
        String query = "select * from user where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));

            user.setGender(Gender.valueOf(resultSet.getString("gender")));
            user.setStatus(UserStatus.valueOf(resultSet.getString("status")));
            user.setPicture(resultSet.getString("picture"));
            user.setTimestamp(resultSet.getString("timestamp"));
        }
        return user;
    }

//    public List<User> getRequestsByFromUserId(long userId) throws SQLException {
//        String query = "select to_id from request where from_id=?";
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        preparedStatement.setLong(1, userId);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        List<User> users = new LinkedList<User>();
//        while (resultSet.next()) {
//            User user = getUserById(resultSet.getInt(1));
//            users.add(user);
//        }
//        return users;
//    }

    public List<User> getRequestsByToUserId(long userId) throws SQLException {
        String query = "select from_id from request where to_id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new LinkedList<User>();
        while (resultSet.next()) {
            User user = getUserById(resultSet.getInt(1));
            users.add(user);
        }
        return users;

    }

    public List<User> getFriendsByUserId(long userId) throws SQLException {
        String query = "select * from friend where (user_id=?) or (friend_id=?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new LinkedList<User>();
        while (resultSet.next()) {
            long friendId;
            User user;
            if (userId == resultSet.getInt(1)) {
                friendId = resultSet.getInt(2);
            } else friendId = resultSet.getInt(1);
            user = getUserById(friendId);
            user.setPassword("");
            users.add(user);
        }
        return users;
    }


    public List<User> getUsersByNameOrSurname(List<String> words) throws SQLException {
        Statement statement = connection.createStatement();
        List<User> userList = new LinkedList<>();
        if (words.size() == 1) {
            String query = "select * from user where name Like '%" + words.get(0) + "%' or surname Like '%" + words.get(0) + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            User user;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPicture(resultSet.getString("picture"));

                userList.add(user);
            }
        } else if (words.size() == 2) {
            String query = "select * from user where name Like '%" + words.get(0) + "%' or surname Like '%" + words.get(1) + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            User user;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPicture(resultSet.getString("picture"));
                userList.add(user);
            }
        }
        return userList;


    }
    public void deleteUserById(long id) throws SQLException {
        String query="delete from user WHERE id='"+id+"'";
        Statement statement=connection.createStatement();
        statement.executeUpdate(query);
    }
}
