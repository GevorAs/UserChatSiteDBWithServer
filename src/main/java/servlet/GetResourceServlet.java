package servlet;

import utils.LoadPathImages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/getResource")
public class GetResourceServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName =  req.getParameter("fileName");
        OutputStream out = resp.getOutputStream();
        String imgDir;

        try {
            LoadPathImages pathImages = new LoadPathImages();
            imgDir=pathImages.loadString();
            FileInputStream in = new FileInputStream(imgDir+"/" + fileName);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }
            in.close();
            out.flush();

        }catch (Exception e){
//            req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

        }

    }
}
