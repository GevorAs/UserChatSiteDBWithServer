package servlet;

import manager.UserManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/addFriend")
public class AddFriendServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        long userRequestId=Long.parseLong(req.getParameter("usersRequest"));
        UserManager userManager = new UserManager();
        try {
            userManager.addFriend(user.getId(),userRequestId);
            userManager.removeRequest(userRequestId,user.getId());
            req.getRequestDispatcher("/home").forward(req,resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);
        }

    }
}
