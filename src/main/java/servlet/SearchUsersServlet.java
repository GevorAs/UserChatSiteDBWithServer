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
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/searchUsers")
public class SearchUsersServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameSurname=req.getParameter("nameSurname");
        try {
        if (nameSurname!=null&&!nameSurname.equals("")) {
            List<String> words = Arrays.asList(nameSurname.split(" "));

            UserManager userManager = new UserManager();


            List<User> userList = userManager.getUsersByNameOrSurname(words);
            req.setAttribute("usersSearch",userList);
        }
            req.getRequestDispatcher("/home").forward(req,resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

        }

    }
}
