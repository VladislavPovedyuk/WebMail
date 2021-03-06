<%--
  User: VladislavP
  Date: 31.10.13
--%>
<%@ include file="/WEB-INF/pages/include.jsp" %>

<c:if test="${!empty message}">
    <div class="success">
            ${message}
    </div>
</c:if>


<fieldset class="smallForm">
    <legend class="formLegend"><h2>Add new address</h2></legend>
    <form:form method="post" commandName="newAddress">

    <form:errors path="*" cssClass="errors" element="div"/>

    Address: <form:input id="txtNewAddress" path="email_address" size="30" maxlength="61"/>
        <input type="submit" value="  Add address  "/>

        </form:form>
</fieldset>

<fieldset class="smallForm">
    <legend class="formLegend"><h2>Address book</h2></legend>
    <c:if test="${!empty currentAddresses}">

        <form:form method="POST" action="/mail/new_address_message">
            <center>
                <input type="submit" value="   Send message to chosen people   "/>
            </center>
            <table style="margin: 5px; padding: 5px;">
                <tr>
                    <th class="td">Choose</th>
                    <th class="td">Address</th>
                    <th class="td"></th>
                </tr>
                <c:forEach items="${currentAddresses}" var="address">
                    <c:set var="rowColor" value="${address.email_address == newAddress.email_address?'red':'white'}"/>
                    <tr style="background-color: ${rowColor}">
                        <td class="td"><input type="checkbox" name="ReceiversId" value="${address.address_id}"/></td>
                        <td class="td">${address.email_address}</td>
                        <td class="td"><a href="/address/remove_address/${address.address_id}">Remove</a></td>
                    </tr>
                </c:forEach>
            </table>
        </form:form>
    </c:if>
</fieldset>