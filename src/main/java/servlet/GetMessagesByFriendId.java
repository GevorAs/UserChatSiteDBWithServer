package servlet;

import manager.MessageManager;
import model.Message;
import model.MessageStatus;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/getMessagesByFriendId")
public class GetMessagesByFriendId extends HomeServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long friendId = 0;

        User user = (User) req.getSession().getAttribute("user");
        MessageManager messageManager = new MessageManager();

            friendId = Long.parseLong(req.getParameter("friendId"));

        try {
            List<Message> friendChat = messageManager.getFriendChat(user.getId(), friendId);
            if (!friendChat.isEmpty()) {
                for (Message message : friendChat) {
                    if (message.getToId()==user.getId()&&message.getStatus().equals(MessageStatus.NEW)) {
                        messageManager.updateMessageStatus(message.getId());

                    }
                }
            }
            req.setAttribute("friendChat", friendChat);
            req.getRequestDispatcher("WEB-INF/ajaxMessage.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);
        }


    }
}
