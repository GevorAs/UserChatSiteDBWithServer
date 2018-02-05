package servlet;

import manager.MessageManager;
import manager.UserManager;
import model.User;
import model.UserStatus;
import utils.CreateDBTables;
import utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreateDBTables.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String errMessage = "";

        if (Validator.isEmpty(email)) {
            errMessage += "Email is empty<br>";
        }
        if (Validator.isEmpty(password)) {
            errMessage += "Password is empty<br>";
        }
        UserManager userManager = new UserManager();
        if (errMessage.equals("")) {
            User user = null;
            try {


                user = userManager.getUserByEmailAndPassword(email, password);


                if (user != null) {


                    user.setStatus(UserStatus.ONLINE);

                    userManager.update(user);

                    user.setPassword(null);
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                   resp.sendRedirect("/home");
                } else {
                    errMessage = "invalid login or password";
                    req.setAttribute("errMessage", errMessage);
                    req.getRequestDispatcher("WEB-INF/loginRegister.jsp").forward(req, resp);
                }
            } catch (SQLException e) {
                req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

            }
        } else {
            req.setAttribute("errMessage", errMessage);
            req.getRequestDispatcher("WEB-INF/loginRegister.jsp").forward(req, resp);
        }

    }
}
