package servlet;

import manager.MessageManager;
import manager.UserManager;
import model.Message;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String toId = req.getParameter("toId");
        MessageManager messageManager = new MessageManager();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");


        List<Message> friendChat = (List<Message>) req.getAttribute("friendChat");

        String newMessage = "";
        UserManager userManager = new UserManager();

        try {
            List<Message> newMessages = messageManager.getNewMessagesToUserId(user.getId());

            if (!newMessages.isEmpty()) {

                newMessage = "new message<br>";
                req.setAttribute("newMessage", newMessage);
                req.setAttribute("newMessages", newMessages);

            }

            List<User> usersRequests = userManager.getRequestsByToUserId(user.getId());


            List<User> friends = userManager.getFriendsByUserId(user.getId());
            req.setAttribute("friendCaht", friendChat);
            req.setAttribute("toId", toId);
            req.setAttribute("usersRequests", usersRequests);
            req.setAttribute("friends", friends);
            req.getRequestDispatcher("WEB-INF/home.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

        }


    }
}
