<%--
  User: Vladislav Povedyuk
  Date: 14.10.13 
--%>

<%@ include file="/WEB-INF/pages/include.jsp" %>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<script type="text/javascript">
    $(function () {
        $("#accordion").accordion({ collapsible: true });
        $("#accordion").accordion("option", "active", false);
        $("#accordion").accordion();
    });
</script>

<fieldset class="hugeForm">
    <legend class="formLegend"><h2>Inbox messages</h2></legend>

    <c:if test="${!empty inbox}">
        <div id="accordion">
            <c:forEach items="${inbox}" var="message">
                <h3>
                    <table>
                        <tr>
                            <td style="width: 20%">From: ${message.sender}</td>
                            <td style="width: 60%">&nbsp&nbsp&nbspSubject: ${message.subject}</td>
                            <td style="width: 20%">When: <fmt:formatDate value="${message.date}"
                                                                        pattern="dd.MM.yyyy HH:mm:ss"/></td>
                        </tr>
                    </table>
                </h3>

                <div>
                    <p>
                    <table>
                        <tr>
                            <td>
                                From: <label>${message.sender}</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                To: <label>${message.receivers}</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Subject: <label>${message.subject}</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Text:
                                <area id="text">
                                    ${message.text}
                                <area>
                            </td>
                        </tr>
                    </table>
                    </p>
                </div>
            </c:forEach>
        </div>
    </c:if>
</fieldset>




