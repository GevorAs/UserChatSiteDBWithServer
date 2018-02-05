
<%@ page import="model.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="manager.UserManager" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%List<Message> messages = (List<Message>) request.getAttribute("friendChat");
    User user = (User) request.getSession().getAttribute("user");
    UserManager userManager = new UserManager();
%>
<ul>
    <%
        if (messages != null) {
            for (Message message : messages) {
                if (message.getFromId() == user.getId()) {
    %>
    <li>
        <div class="message-data">
                        <span class="message-data-name"><i
                                class="fa fa-circle online"></i><%=userManager.getUserById(message.getFromId()).getName()%></span>
            <span class="message-data-time"><%=message.getTimestamp()%></span>
        </div>
        <div class="message my-message">
            <%=message.getMessage()%>
        </div>
    </li>
    <% } else {%>
    <li class="clearfix">
        <div class="message-data align-right">
            <span class="message-data-time"><%=message.getTimestamp()%></span> &nbsp; &nbsp;
            <span class="message-data-name"><%=userManager.getUserById(message.getFromId()).getName()%></span>
            <i class="fa fa-circle me"></i>
        </div>
        <div class="message other-message float-right">
            <%=message.getMessage()%>
        </div>
    </li>
    <%
                }
            }
        }
    %>
</ul>