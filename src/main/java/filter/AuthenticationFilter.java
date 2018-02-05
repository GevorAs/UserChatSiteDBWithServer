package filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/home"})
public class AuthenticationFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //load from property

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
//        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
//        if(excludeUrls.contains(requestURI)){
//            filterChain.doFilter(servletRequest,servletResponse);
//        }
        if (user == null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendRedirect("WEB-INF/loginRegister.jsp");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
