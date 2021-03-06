<%--
  User: Vladislav Povedyuk
  Date: 22.10.13 
--%>
<%@ include file="/WEB-INF/pages/include.jsp" %>

<form:form method="post" commandName="userToEdit">

    <form:errors path="*" cssClass="errors" element="div"/>

    <fieldset class="smallForm">
        <legend class="formLegend"><h2>Edit user</h2></legend>
        <table>

            <tr>
                <td>ID:</td>
                <td><form:input path="user_id" readonly="true" cssStyle="border: none;"/></td>
            </tr>

            <tr>
                <td>Email:</td>
                <c:set var="inputSize" value="${fn:length(userToEdit.email)}"/>
                <td><form:input path="email" readonly="true" size="${inputSize}" cssStyle="border: none;"/></td>
            </tr>

            <tr>
                <td>Password:</td>
                <td><form:input path="password" size="30" maxlength="61"/></td>
            </tr>

            <tr>
                <td>Role:</td>
                <td><form:select path="role">
                    <form:option value="ROLE_USER" label="ROLE_USER"/>
                    <form:option value="ROLE_ADMIN" label="ROLE_ADMIN"/>
                </form:select></td>
            </tr>

            <tr>
                <td>isDisabled:</td>
                <td><form:checkbox path="isDisabled"/></td>
            </tr>


            <tr>
                <td><input type="submit" value="  Submit Edit "/></td>
                <td><input type="button" value="  Cancel  " onclick="window.history.back()"/></td>
            </tr>
        </table>
    </fieldset>

</form:form>