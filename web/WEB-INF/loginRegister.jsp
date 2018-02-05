<%--
  Created by IntelliJ IDEA.
  User: Arianna
  Date: 26.01.2018
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login</title>
    <link href="../css/loginPageStyle.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="../js/loginPafge.js"></script>


</head>
<body>
<div class="main-wrapper">


    <div class="veen">
        <div class="cotn_principal">
            <div class="cont_centrar">

                <div class="cont_login">
                    <div class="cont_info_log_sign_up">
                        <div class="col_md_login">
                            <div class="cont_ba_opcitiy">

                                <h2>LOGIN</h2>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                <button class="btn_login" onclick="cambiar_login()">LOGIN</button>
                            </div>
                        </div>
                        <div class="col_md_sign_up">
                            <div class="cont_ba_opcitiy">
                                <h2>SIGN UP</h2>


                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>

                                <button class="btn_sign_up" onclick="cambiar_sign_up()">SIGN UP</button>
                            </div>
                        </div>
                    </div>


                    <div class="cont_back_info">
                        <div class="cont_img_back_grey">
                            <img src="https://images.unsplash.com/42/U7Fc1sy5SCUDIu4tlJY3_NY_by_PhilippHenzler_philmotion.de.jpg?ixlib=rb-0.3.5&q=50&fm=jpg&crop=entropy&s=7686972873678f32efaf2cd79671673d"
                                 alt=""/>
                        </div>

                    </div>
                    <div class="cont_forms">
                        <div class="cont_img_back_">
                            <img src="https://images.unsplash.com/42/U7Fc1sy5SCUDIu4tlJY3_NY_by_PhilippHenzler_philmotion.de.jpg?ixlib=rb-0.3.5&q=50&fm=jpg&crop=entropy&s=7686972873678f32efaf2cd79671673d"
                                 alt=""/>
                        </div>
                        <form action="/login" method="post">
                            <div class="cont_form_login">
                                <a href="#" onclick="ocultar_login_sign_up()"><i class="material-icons"></i></a>
                                <%
                                    if (request.getAttribute("errMessage") != null ) {
                                %>
                                <h3 style="color: red"><%=request.getAttribute("errMessage")%>
                                </h3>
                                <%
                                    }
                                %>
                                <h2>LOGIN</h2>
                                <input type="text" placeholder="Email" name="email"/>
                                <input type="password" placeholder="Password" name="password"/>
                                <input type="submit" class="btn_login" onclick="cambiar_login()" value="LOGIN">
                                <%--<button type="submit" class="btn_login" onclick="cambiar_login()">LOGIN</button>--%>
                            </div>
                        </form>
                        <form action="/register" method="post" enctype="multipart/form-data">
                            <div class="cont_form_sign_up">
                                <a href="#" onclick="ocultar_login_sign_up()"><i class="material-icons"></i></a>
                                <%
                                    if (request.getAttribute("errMessage") != null ) {
                                %>
                                <span style="color: red;float: right"><%=request.getAttribute("errMessage")%></span>
                                <%
                                    }
                                %>
                                <h2>SIGN UP</h2>
                                <input type="text" placeholder="Name" name="name"/>
                                <input type="text" placeholder="Surame" name="surname"/>
                                <input type="text" placeholder="Email" name="email"/>
                                <input type="password" placeholder="Password" name="password"/>
                                <input type="password" placeholder="Confirm Password" name="repassword"/>
                                <select name="gender">
                                    <option value="MALE">MALE</option>
                                    <option value="FEMALE">FEMALE</option>
                                </select>
                                <input type="file" name="picture">
                                <input type="submit" class="btn_sign_up" onclick="cambiar_sign_up()" value="SIGN UP">
                                <%--<button type="submit" class="btn_sign_up" onclick="cambiar_sign_up()">SIGN UP</button>--%>

                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-2.2.0.min.js"
        integrity="sha256-ihAoc6M/JPfrIiIeayPE9xjin4UWjsx2mjW/rtmxLM4=" crossorigin="anonymous"></script>

<script>
    $(document).ready(function () {

        $('.main-wrapper').mousemove(function (e) {
            var offset = $(this).offset();
            var relativeX = e.pageX - offset.left;
            var relativeY = e.pageY - offset.top;
            var movex = (-relativeX / 5);
            var movey = (-relativeY / 5);
            $(this).css({
                'background-position-x': movex,
                'background-position-y': movey
            });
        });
        $('.tada').mousemove(function (e) {
            var offset = $(this).offset();
            var relativeX = e.pageX - offset.left;
            var relativeY = e.pageY - offset.top;
            var movex = (-relativeX / 5);
            var movey = (-relativeY / 5);
            $(this).css({
                'background-position-x': movex,
                'background-position-y': movey
            });
        });

    });

</script>


</body>
</html>
