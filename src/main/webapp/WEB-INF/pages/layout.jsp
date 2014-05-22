<%--
  User: Vladislav Povedyuk
  Date: 08.10.13 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/WEB-INF/pages/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru" lang="ru">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>MAIL</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" type="text/css"/>
</head>

<body>
<div id="wrapper">

    <!-- Title -->
    <div class="title">
        <div class="title-top">
            <div class="title-left">
                <div class="title-right">
                    <div class="title-bottom">
                        <div class="title-top-left">
                            <div class="title-bottom-left">
                                <div class="title-top-right">
                                    <div class="title-bottom-right">
                                        <h1><a href="/login" title="Go to homepage">WEB <span>MAIL</span></a></h1>

                                        <p>APPLICATION</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Title end -->

    <hr class="noscreen"/>

    <div class="content">

        <div class="column-left">
            <tiles:insertAttribute name="sidebar"/>
        </div>

        <div id="skip-menu"></div>
        <div class="column-right">
            <div class="box">
                <div class="box-top"></div>
                <div class="box-in">

                    <tiles:insertAttribute name="body"/>

                </div>
            </div>

            <div class="box-bottom">

                <hr class="noscreen"/>

                <div class="footer-info-left"><a href="http://www.linkedin.com/profile/view?id=142856169"
                                                 target="blank">Povedyuk Vladislav</a>, 2013.
                </div>

            </div>

            <div class="cleaner">&nbsp;</div>
        </div>
    </div>

</body>
</html>


