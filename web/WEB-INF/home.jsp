<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Message" %>
<%@ page import="manager.UserManager" %>
<%@ page import="model.UserStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Refresh" content="15" />
    <title>My Account</title>
    <link href='https://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="../css/homePageChat.css">
    <link rel="stylesheet" href="../css/HomePageSearch.css">
    <link rel="stylesheet" href="../css/list.css">
    <link rel="stylesheet" href="../css/newMessageAndRequest.css">
</head>
<body>
<% User user = (User) request.getSession().getAttribute("user");
    List<User> friends = (List<User>) request.getAttribute("friends");
    UserManager userManager = new UserManager();
    String toIdStr = (String) request.getAttribute("toId");
    List<User> usersSearch = (List<User>) request.getAttribute("usersSearch");
    String newMessage = (String) request.getAttribute("newMessage");
    List<Message> newMessages = (List<Message>) request.getAttribute("newMessages");
    List<User> usersRequests = (List<User>) request.getAttribute("usersRequests");
%>

<div class="wrapper">
    <div class="search-container">
        <a href="#" id="searchUser" onclick="getText();" class="search-button">
            <i class="fa fa-search"></i>
        </a>
        <input id="searchText" type="text" placeholder="Search">
    </div>
    <div>
        <%--<button id="btnReload">Reload</button>--%>
        <ul class="rolldown-list" id="myList">
            <% if (usersSearch != null && !usersSearch.isEmpty()) {
                for (User user1 : usersSearch) {
                    if (user.getId() != user1.getId()) {
            %>
            <li>
                <img src="/getResource?fileName=<%=user1.getPicture()%>" alt="img"
                     style="width: 45px;margin-right: 5px"/>
                <%=user1.getName()%>&nbsp;<%=user1.getSurname()%>
                <%--<input type="hidden" value="<%=user1.getId()%>" name="reqToId">--%>
                <a href="/sendRequest?reqToId=<%=user1.getId()%>"> <input type="submit" value="Request"
                                                                          style="float: right">
                </a>
            </li>
            <%
                        }
                    }
                }
            %>
        </ul>
    </div>

</div>
<a style="margin: 50px" href="/home">
    <button>HOME</button>
</a>
<a href="/logout">
    <button>Logout</button>
</a><br>
<div style="width: 250px">
    <%
        if (!usersRequests.isEmpty()) {

    %>
    <ul id="columns">
        <p>New Request</p>
        <%
            for (User usersRequest : usersRequests) {

        %>
        <li class="column">
            <header><a href="/addFriend?usersRequest=<%=usersRequest.getId()%>" style="float: left" id="aaa">
                <button style="margin-right: 5px;color: #0dff93">Add</button>
                <%=usersRequest.getName()%>&nbsp;&nbsp;<%=usersRequest.getSurname()%>
            </a> <a href="/removeRequest?usersRequest=<%=usersRequest.getId()%>" style="float: right">
                <button style="color: red">Ignore</button>
            </a></header>
        </li>
        <%}%>
    </ul>


    <% }
    %>


</div>

<div style="width: 250px">
    <%
        if (newMessage != null && !newMessage.equals("") && !newMessages.isEmpty()) {
    %>
    <ul id="columns">
        <p style="color: blueviolet"><%=newMessage%>
        </p>
        <%
            for (Message message : newMessages) {
        %>
        <li class="column">
            <header>
                <%=userManager.getUserById(message.getFromId()).getName()%>&nbsp;&nbsp;<%=userManager.getUserById(message.getFromId()).getSurname()%>

                <button style="color: green;float: right;margin-right: 5px"
                        onclick="getMessageAjax(<%=message.getFromId()%>)">See
                </button>
            </header>
        </li>
        <%}%>
    </ul>


    <% }
    %>


</div>
<div style="height: 500px; width:100%">
    <div class="container clearfix">
        <div class="people-list" id="people-list">
            <div class="search">
                <input type="text" placeholder="search"/>
                <i class="fa fa-search"></i>
            </div>
            <%--user friend--%>
            <ul class="list">
                <%
                    if (friends != null) {
                        for (User friend : friends) {
                            if (friend.getStatus().equals(UserStatus.ONLINE)) {
                %>
                <%--/getMessagesByFriendId?friendId=<%=friend.getId()%>--%>
                <li class="clearfix">
                    <div onclick="getMessageAjax(<%=friend.getId()%>)">
                        <img src="/getResource?fileName=<%=friend.getPicture()%>" style="width: 50%" alt="avatar"/>
                        <div class="about">
                            <div class="name"><p style="color: white"><%=friend.getName() + " " + friend.getSurname()+" "+friend.getGender().name()%>
                            </p>
                            </div>
                            <div class="status">
                                <i class="fa fa-circle online"></i> <%=friend.getStatus()%>
                            </div>

                        </div>


                    </div>
                    <div><a href="/deleteFriend?deleteFriendId=<%=friend.getId()%>">
                        <button>Delete</button>
                    </a></div>

                </li>
                <p>------------------------------</p>

                <%
                } else {
                %>

                <li class="clearfix">
                    <div onclick="getMessageAjax(<%=friend.getId()%>)">
                        <img src="/getResource?fileName=<%=friend.getPicture()%>" style="width: 50%" alt="avatar"/>
                        <div class="about">
                            <div class="name"><%=friend.getName() + " " + friend.getSurname()+""+friend.getGender().name()%>
                            </div>
                            <div class="status">
                                <i class="fa fa-circle offline"></i> <%=friend.getStatus()%>
                            </div>
                        </div>
                    </div>
                    <div><a href="/deleteFriend?deleteFriendId=<%=friend.getId()%>">
                        <button>Delete</button>
                    </a></div>

                </li>
                <p>------------------------------</p>
                <%
                            }
                        }
                    }
                %>
            </ul>
        </div>
        <div class="chat">
            <div class="chat-header clearfix">

                <img src="/getResource?fileName=<%=user.getPicture()%>" style="width: 25%" alt="avatar"/>
                <div class="chat-about">
                    <div class="chat-with"><p style="font-size: 12px">Welcome to home<h3> <%=user.getName()%>&nbsp;<%=user.getSurname()%>
                    </h3></p>
                        <p style="font-size: 10px">Account Created date: <%=user.getTimestamp()%></p>
                    </div>
                    <%--<div class="chat-num-messages">already 1 902 messages</div>--%>
                </div>
                <i class="fa fa-star"></i>

            </div> <!-- end chat-header -->
            <div class="chat-history" id="div">

            </div> <!-- end chat-history -->
            <form action="/sendMessage" method="post">
                <div class="chat-message clearfix">
                <textarea name="message-to-send" id="message-to-send" placeholder="Type your message"
                          rows="3"></textarea>
                    <input type="hidden" name="toId" value="" id="sendId">
                    <%--<i class="fa fa-file-o"></i> &nbsp;&nbsp;&nbsp;--%>
                    <%--<i class="fa fa-file-image-o"></i>--%>
                    <button>Send</button>
                </div> <!-- end chat-message -->
            </form>
        </div> <!-- end chat -->
    </div> <!-- end container -->
</div>
<div style="margin-top: 40%">
    <a href="/deleteAccount"><button>DELETE ACCOUNT</button></a>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js'></script>
<script src="../js/index.js"></script>
<script src="../js/list.js"></script>
<%--<script src="../js/newMessageAndRequest.js"></script>--%>

<script>
    var x;
    var y;
    <%  if (toIdStr!=null&&!toIdStr.equals("")){
%>
    getMessageAjax(<%=toIdStr%>);
    <%
    }
%>

    function getText() {
        x = "/searchUsers?nameSurname=" + document.getElementById("searchText").value;
        $("#searchUser").attr("href", x);
    }

    function getMessageAjax(id) {
        $("#sendId").val(id);
        if (!y) {
            y = setInterval(function () {
                $.ajax({
                    url: "http://localhost:8080/getMessagesByFriendId?friendId=" + id, success: function (result) {
                        $("#div").html(result);
                    }
                });
            }, 1000)
        } else {
            clearInterval(y)
            y = setInterval(function () {
                $.ajax({
                    url: "http://localhost:8080/getMessagesByFriendId?friendId=" + id, success: function (result) {
                        $("#div").html(result);
                    }
                });
            }, 1000)
        }
    }
</script>
</body>
</html>