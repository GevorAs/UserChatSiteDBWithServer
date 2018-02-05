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

@WebServlet(urlPatterns = "/sendRequest")
public class SendRequestServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long toId=Long.parseLong(req.getParameter("reqToId"));
        User user = (User) req.getSession().getAttribute("user");
        UserManager userManager = new UserManager();
        try {
            userManager.addRequest(user.getId(),toId);
            req.getRequestDispatcher("/home").forward(req,resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

        }
    }
}
