package servlet;

import manager.MessageManager;
import model.Message;
import model.MessageStatus;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/sendMessage")
public class SendMessageServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String toIdStr = req.getParameter("toId");
        try {

            long toId = Long.parseLong(toIdStr);
            String text = req.getParameter("message-to-send");
            if (text!=null&&!text.equals("")) {
                MessageManager messageManager = new MessageManager();
                User user = (User) req.getSession().getAttribute("user");
                Message message = new Message();
                message.setFromId(user.getId());
                message.setToId(toId);
                message.setMessage(text);
                message.setStatus(MessageStatus.NEW);
                messageManager.add(message);
                req.setAttribute("toId", toIdStr);
                req.getRequestDispatcher("/home").forward(req, resp);
            }else  req.getRequestDispatcher("/home").forward(req, resp);
        } catch (NumberFormatException e) {
            req.getRequestDispatcher("/home").forward(req, resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

        }

    }
}
