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
    <title>
        <fmt:message key="vacancyName"/>
    </title>
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
        <input type="hidden" name="command" value="result"/>
        <label>
            <input type="submit" value="<fmt:message key="result"/>"/>
        </label>
    </form>
    <form method="POST" action="controller">
        <input type="hidden" name="command" value="go_my_profile"/>
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
<h3 class="Registr">${requestScope.oneVacancy.vacancy}</h3>
<hr/>
<table border="1">
    <tr>
        <td><fmt:message key="companyName"/></td>
        <td><fmt:message key="vacancyName"/></td>
        <td><fmt:message key="salary"/></td>
        <td><fmt:message key="other"/></td>
    </tr>
    <tr>
        <td>${requestScope.oneVacancy.company}</td>
        <td>${requestScope.oneVacancy.vacancy}</td>
        <td>${requestScope.oneVacancy.salary}</td>
        <td>${requestScope.oneVacancy.other}</td>
        <td>
            <form method="POST" action="controller">
                <input type="hidden" name="command" value="add_proposal"/>
                <input type="hidden" name="id" value="${requestScope.oneVacancy.vacancyId}"/>
                <input type="submit" class="button btnProfile" value="<fmt:message key="addProposal"/>"/>
            </form>
        </td>

    </tr>
</table>
<footer>
    <h4><ex:Info/></h4>
</footer>
</body>
</html>