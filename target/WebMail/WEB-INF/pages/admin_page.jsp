<%--
  User: Vladislav Povedyuk
  Date: 21.10.13 
--%>
<%@ include file="/WEB-INF/pages/include.jsp" %>
<form:form method="post" commandName="newUser">

    <form:errors path="*" cssClass="errors" element="div"/>
    <c:if test="${!empty message}">
        <div class="success">
                ${message}
        </div>
    </c:if>


    <fieldset class="smallForm">
        <legend class="formLegend"><h2>Add new user</h2></legend>
        <table>
            <tr>
                <td>Email:</td>
                <td><form:input path="email" size="30" maxlength="61"/></td>
                <td><span class="error"><form:errors path="email"/></span></td>
            </tr>

            <tr>
                <td>Password:</td>
                <td><form:password path="password"/></td>
                <td><span class="error"><form:errors path="password"/></span></td>
            </tr>

            <tr>
                <td>Password confirm:</td>
                <td><form:password path="passwordConfirm"/></td>
                <td><span class="error"><form:errors path="passwordConfirm"/></span></td>
            </tr>
            <tr>
                <center>
                    <td colspan="3"><input type="submit" value="  Add new user  "/></td>
                </center>
            </tr>
        </table>
    </fieldset>

</form:form>

<fieldset class="smallForm">
    <legend class="formLegend"><h2>Users</h2></legend>

    <c:if test="${!empty userList}">
        <table style="margin: 5px; padding: 5px;">
            <tr>
                <th class="td">ID</th>
                <th class="td">Email</th>
                <th class="td">Role</th>
                <th class="td">isDisabled</th>
                <th class="td">Edit user</th>

            </tr>
            <c:forEach items="${userList}" var="user">
                <c:set var="rowColor" value="${user.isDisabled == true?'red':'white'}"/>
                <c:set var="fontColor" value="${user.email == newUser.email?'green':'black'}"/>
                <c:set var="fontStyle" value="${fontColor == 'green'?'italic':'normal'}"/>
                <tr style="background-color: ${rowColor}; color: ${fontColor}; font-style: ${fontStyle}">
                    <td class="td">${user.user_id}</td>
                    <td class="td">${user.email}</td>
                    <td class="td">${user.role}</td>
                    <td class="td">${user.isDisabled}</td>
                    <td class="td"><a href="/admin/edit_user/${user.user_id}">Edit</a></td>
                </tr>

            </c:forEach>
        </table>
    </c:if>

</fieldset>
