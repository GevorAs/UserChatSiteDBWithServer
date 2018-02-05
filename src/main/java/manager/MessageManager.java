package manager;

import db.DBConnectionProvider;
import model.Message;
import model.MessageStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MessageManager {
  private final    Connection connection;
    public MessageManager() {
        connection= DBConnectionProvider.getInstance().getConnection();
    }
    public void add(Message message) throws SQLException {
        String query="insert into message (title,message,from_id,to_id,status)" +
                "values(?,?,?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,message.getTitle());
        preparedStatement.setString(2,message.getMessage());
        preparedStatement.setLong(3,message.getFromId());
        preparedStatement.setLong(4,message.getToId());
        preparedStatement.setString(5,message.getStatus().name());
        preparedStatement.executeUpdate();
        ResultSet resultSet=preparedStatement.getGeneratedKeys();
        if (resultSet.next()){
            message.setId(resultSet.getInt(1));
        }
    }
    public void updateMessageStatus(long id) throws SQLException {
        String query="update message set status=? where (id=?)";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,String.valueOf(MessageStatus.OLD));
        preparedStatement.setLong(2,id);
        preparedStatement.executeUpdate();
    }


    public List<Message> getFriendChat(long userId,long friendId) throws SQLException {
        String query="select * from message where (to_id=? and from_id=?) or (from_id=? and to_id=?)";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setLong(1,userId);
        preparedStatement.setLong(2,friendId);
        preparedStatement.setLong(3,userId);
        preparedStatement.setLong(4,friendId);
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Message> messages=new LinkedList<Message>();
        while (resultSet.next()){
            Message message = new Message();
            message.setId(resultSet.getInt(1));
            message.setTitle(resultSet.getString(2));
            message.setMessage(resultSet.getString(3));
            message.setFromId(resultSet.getInt(4));
            message.setToId(resultSet.getInt(5));
            message.setStatus(MessageStatus.valueOf(resultSet.getString(6)));
            message.setTimestamp(resultSet.getString(7));
            messages.add(message);
        }
        return messages;
    }


    public List<Message> getNewMessagesToUserId(long userId) throws SQLException {
        String query="select * from message where (to_id=?) and status='NEW'";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Message> messages = new LinkedList<Message>();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt(1));
                message.setTitle(resultSet.getString(2));
                message.setMessage(resultSet.getString(3));
                message.setFromId(resultSet.getInt(4));
                message.setToId(resultSet.getInt(5));
                message.setStatus(MessageStatus.valueOf(resultSet.getString(6)));
                message.setTimestamp(resultSet.getString(7));
                messages.add(message);
            }
            return messages;

    }

}
