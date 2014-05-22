<%--
  User: Vladislav Povedyuk
  Date: 18.10.13 
--%>
<%@ include file="/WEB-INF/pages/include.jsp" %>
<script src="http://js.nicedit.com/nicEdit-latest.js" type="text/javascript"></script>
<script type="text/javascript">bkLib.onDomLoaded(nicEditors.allTextAreas);</script>
<script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.autocomplete.min.js" />"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#receivers').autocomplete({
            serviceUrl: '/mail/new_message/getAddressBook',
            paramName: "email_address",
            delimiter: ", ",
            transformResult: function (response) {
                return {
                    suggestions: $.map($.parseJSON(response), function (item) {
                        return { value: item, data: item + ', ' };
                    })
                };
            }
        });
    });
</script>

<form:form method="post" commandName="newMessage" action="/mail/new_message">
    <form:errors path="*" cssClass="errors" element="div"/>

    <c:if test="${!empty message}">
        <div class="success">
                ${message}
        </div>
    </c:if>
    <fieldset class="smallForm">
        <legend class="formLegend"><h2>Create new message</h2></legend>
        <table>
            <tr>

                <td>To:</td>
                <td><form:input id="receivers" path="receivers" size="60"/></td>
                <td><span class="error"><form:errors path="receivers"/></span></td>
            </tr>

            <tr>
                <td>Subject:</td>
                <td><form:input path="subject" size="60"/></td>
                <td><span class="error"><form:errors path="subject"/></span></td>
            </tr>
            <tr>
                <td>Text:</td>
                <td colspan="2"><form:textarea path="text" cssStyle="width: 500px; height: 200px;"/></td>
            </tr>
            <tr>
                <td colspan="3">
                    <center>
                        <input type="submit" value="  Send e-mail  "/>
                    </center>
                </td>
            </tr>
        </table>
    </fieldset>
</form:form>