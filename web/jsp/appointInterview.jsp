<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>
<c:set var="language" value="${requestScope.lang}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources.text"/>
<!DOCTYPE html>
<html lang="${language}">

<head>
    <title><fmt:message key="appoint"/></title>
    <style>
        <%@include file='../css/final.css' %>
    </style>
</head>
<body>
<header>
    <div class="logo">
        <h3>HR-system</h3>
    </div>
    <form method="POST" action="controller">
        <input type="hidden" name="command" value="go_approved"/>
        <label>
            <input type="submit" value="<fmt:message key="approved"/>"/>
        </label>
    </form>
    <form method="POST" action="controller">
        <input type="hidden" name="command" value="go_hr_profile"/>
        <label>
            <input type="submit" value="<fmt:message key="myProfile"/>"/>
        </label>
    </form>
    <form method="POST" action="controller">
        <input type="hidden" name="command" value="get_vacancies"/>
        <input type="hidden" name="page" value="1"/>
        <input type="hidden" name="direction" value=""/>
        <label>
            <input type="submit" value="<fmt:message key="vacancy"/>"/>
        </label>
    </form>
    <form method="POST" action="controller">
        <input type="hidden" name="command" value="logout"/>
        <label>
            <input type="submit" value="<fmt:message key="LogOut"/>"/>
        </label>
    </form>
</header>
<h3 class="Registr"><fmt:message key="appointInterview"/></h3>
<hr/>
<main>
    <table border="1">
        <tr>
            <td><fmt:message key="name"/></td>
            <td><fmt:message key="sname"/></td>
            <td><fmt:message key="vacancyName"/></td>
            <td><fmt:message key="companyName"/></td>
        </tr>
        <tr>

            <td>${requestScope.info.name}</td>
            <td>${requestScope.info.sname}</td>
            <td>${requestScope.info.vacancy}</td>
            <td>${requestScope.info.company}</td>
        </tr>
    </table>
    <form method="POST" action="controller" class="addIntForm">
        <input type="hidden" name="command" value="add_interview"/>
        <br/><fmt:message key="date"/>:<input type="text" name="date" value=""
                                              placeholder="<fmt:message key="dateFormat"/>"/><br/>
        <br/><fmt:message key="time"/>:<input type="text" name="time" value=""
                                              placeholder="<fmt:message key="timeFormat"/>"/><br/>
        <br/><fmt:message key="place"/>:<input type="text" name="place" value=""/><br/>
        <input type="hidden" name="userId" value="${requestScope.info.userId}"/>
        <input type="hidden" name="vacancyId" value="${requestScope.info.vacancyId}"/>
        <input type="hidden" name="type" value="${requestScope.info.type}"/>
        <input type="submit" class="button btnProfile" value="<fmt:message key="appoint"/>"/>
    </form>
    <br>
    ${requestScope.errorParse}
    <br/>
</main>

<footer>
    <h4><ex:Info/></h4>
</footer>
</body>
</html>
