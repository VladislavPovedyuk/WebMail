<%--
  User: Vladislav Povedyuk
  Date: 09.10.13
--%>
<%@ include file="/WEB-INF/pages/include.jsp" %>

<%@page contentType="text/html; charset=UTF-8" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<body OnLoad="document.user_login.j_username.focus();">

<form id="user_login" name="user_login" commandName="new_user" method="post" action="/j_spring_security_check">


    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
        <div class="errors">
            Login error : Please try again.<br/>Root Cause:
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>



    <fieldset class="smallForm">
        <legend class="formLegend"><h2>Login</h2></legend>
        <table>
            <tr>
                <td align="right">Email:&nbsp&nbsp&nbsp</td>
                <td><input id="j_username" name="j_username" type="text" size="30"/></td>
            </tr>

            <tr>
                <td align="right">Password:&nbsp&nbsp&nbsp</td>
                <td><input id="j_password" name="j_password" type="password"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Login" style="width: 150px"></td>
                <td>&nbsp&nbsp&nbspRemember me:
                    <input type="checkbox" name="_spring_security_remember_me">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    If you don't have any account you may&nbsp
                    <a href="/register"> Register</a>
                </td>
            </tr>
        </table>
    </fieldset>
</form>


