<%--
  User: Vladislav Povedyuk
  Date: 09.10.13 
--%>
<%@ include file="/WEB-INF/pages/include.jsp" %>
<%@page contentType="text/html; charset=UTF-8" %>


<form:form method="post" commandName="userForRegister">

    <form:errors path="*" cssClass="errors" element="div"/>

    <fieldset class="smallForm">
        <legend class="formLegend"><h2>Create an Account</h2></legend>
        <table>
            <tr>
                <td>Email:</td>
                <td><form:input path="email" size="30"  maxlength="61"/></td>
                <td><span class="error"><form:errors path="email"/></span></td>
            </tr>

            <tr>
                <td>Password:</td>
                <td><form:password path="password" maxlength="61"/></td>
                <td><span class="error"><form:errors path="password"/></span></td>
            </tr>

            <tr>
                <td>Password confirm:</td>
                <td><form:password path="passwordConfirm" maxlength="61"/></td>
                <td><span class="error"><form:errors path="passwordConfirm"/></span></td>
            </tr>
            <tr>
                <td colspan="3"><input type="submit" value="  Register  "/></td>
            </tr>
        </table>
    </fieldset>

</form:form>

