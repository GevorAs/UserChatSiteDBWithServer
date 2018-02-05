package servlet;

import manager.UserManager;
import model.Gender;
import model.User;
import model.UserStatus;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.LoadPathImages;
import utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


//       ----------------------------------------------------------------------------------------


        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (isMultipart) {
            LoadPathImages pathImages = new LoadPathImages();
            String imageUploadPath = pathImages.loadString();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // maximum size that will be stored in memory
            factory.setSizeThreshold(500 * 1024);
            // Location to save data that is larger than maxMemSize.
            factory.setRepository(new File(""));
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // maximum file size to be uploaded.
            upload.setSizeMax(10000 * 1024);
            String name = "";
            String surname = "";
            String email = "";
            String password = "";
            String repassword = "";
            String gender = "";
            String picture = "";

            try {
                // Parse the request to get file items.
                List<FileItem> fileItems = upload.parseRequest(req);
                for (FileItem fileItem : fileItems) {
                    if (fileItem.isFormField()) {
                        if (fileItem.getFieldName().equals("name")) {
                            name = fileItem.getString();
                        } else if (fileItem.getFieldName().equals("surname")) {
                            surname = fileItem.getString();
                        } else if (fileItem.getFieldName().equals("email")) {
                            email = fileItem.getString();
                        } else if (fileItem.getFieldName().equals("password")) {
                            password = fileItem.getString();
                        } else if (fileItem.getFieldName().equals("repassword")) {
                            repassword = fileItem.getString();
                        } else if (fileItem.getFieldName().equals("gender")) {
                            gender = fileItem.getString();
                        }
                    } else {
                        String picName = System.currentTimeMillis() + "_" + fileItem.getName();
                        File file = new File(imageUploadPath+"/" + picName);
                        fileItem.write(file);
                        picture = picName;
                    }


                }


            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            String errMessage = "";
            UserManager userManager = new UserManager();

            if (Validator.isEmpty(name)) {
                errMessage += "Name is empty<br>";
            }
            if (Validator.isEmpty(email)) {
                errMessage += "Email is empty<br>";
            } else {
                try {
                    if (userManager.isEmailExist(email)) {
                        errMessage += "Email is already exist<br>";
                    }
                } catch (SQLException e) {
                    req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

                }
            }
            if (Validator.isEmpty(password)) {
                errMessage += "Password is empty<br>";
            }
            if (password.length() < 6) {
                errMessage += "Password is small 6<br>";
            }
            if (!password.equals(repassword)) {
                errMessage += "Password and Re-password not equals";
            }
            if (errMessage.equals("")) {
                User user = new User(name, surname, email, password,
                        Gender.valueOf(gender.toUpperCase()),
                        UserStatus.ONLINE, picture);
                try {
                    userManager.add(user);
                    user.setPassword(null);
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    req.getRequestDispatcher("/home").forward(req, resp);
                } catch (SQLException e) {
                    req.getRequestDispatcher("WEB-INF/err.jsp").forward(req, resp);

                }

            } else {
                req.setAttribute("errMessage", errMessage);
                req.getRequestDispatcher("WEB-INF/loginRegister.jsp").forward(req, resp);
            }


        }

//       ----------------------------------------------------------------------------------------


    }
}
