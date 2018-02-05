package servlet;

import manager.UserManager;
import model.User;
import model.UserStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        UserManager userManager = new UserManager();
        try {
            User userById = userManager.getUserById(user.getId());
            userById.setStatus(UserStatus.OFFLINE);
            userManager.update(userById);
            req.getSession().invalidate();
            req.getRequestDispatcher("/index.jsp").forward(req,resp);
        } catch (SQLException e) {
            req.getSession().invalidate();
            req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

        }

    }
}
