<%--
  User: Vladislav Povedyuk
  Date: 14.10.13 
--%>
<%@ include file="/WEB-INF/pages/include.jsp" %>

<h3>MENU :</h3>
<a href="#skip-menu" class="hidden"></a>
<ul class="menu">
    <li>
        You entered like:
        ${username}
        <p>&nbsp;

        <p>&nbsp;
            <a href="<c:url value="/j_spring_security_logout" />">Log out</a>

    </li>
    <li><a href="/mail/new_message"> NEW MESSAGE </a></li>
    <li><a href="/mail/inbox" onclick="return false" class="active"> INBOX </a></li>
    <li><a href="/mail/outbox"> OUTBOX </a></li>
    <li><a href="/mail/sent"> SENT </a></li>
    <li><a href="/address/address_book"> ADDRESS BOOK </a></li>

    <sec:authorize ifAllGranted="ROLE_ADMIN">
        <li>
            <a href="<c:url value="/admin/page"/>">ADMIN PAGE</a>
        </li>
    </sec:authorize>

</ul>