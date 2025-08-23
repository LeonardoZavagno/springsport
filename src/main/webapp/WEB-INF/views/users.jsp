<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>View Users</title>
    </head>
    <body>
        <a href="/">Home</a>
        <br/>
        <a href="logout">Logout</a>
        <br/>
        <hr>
        <c:if test="${not empty message}">
            <p style="color:green;">${message}</p>
        </c:if>
        <c:choose>
            <c:when test="${not empty user.user_id}">
                <h1>Edit User</h1>
                <c:url var="form_url" value="/users/update/${user.user_id}"/>
            </c:when>
            <c:otherwise>
                <h1>New User</h1>
                <c:url var="form_url" value="/users/create"/>
            </c:otherwise>
        </c:choose>
        <form:form action="${form_url}" method="post" modelAttribute="user">
            <form:hidden path="user_id"/>
            <table>
                <tbody>
                    <tr>
                        <td><form:label path="user_name">NAME:</form:label></td>
                        <td><form:input type="text" path="user_name" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="user_surname">SURNAME:</form:label></td>
                        <td><form:input type="text" path="user_surname" maxlength="50"/></td>
                    </tr>
                </tbody>
            </table>
            <c:choose>
                <c:when test="${not empty user.user_id}">
                    <input type="submit" value="Update"/>
                    <a href="/users">Cancel</a>
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Create"/>
                </c:otherwise>
            </c:choose>
        </form:form>
        <br/>
        <hr>
        <h1>List Users</h1>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>SURNAME</th>
                    <th colspan="2">ACTIONS</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.user_id}</td>
                        <td>${user.user_name}</td>
                        <td>${user.user_surname}</td>
                        <td>
                            <c:url var="edit_url" value="/users/edit/${user.user_id}"/>
                            <a href="${edit_url}">Edit</a>
                        </td>
                        <td>
                            <c:url var="delete_url" value="/users/delete/${user.user_id}"/>
                            <form:form action="${delete_url}" method="post">
                                <input type="submit" value="Delete"/>
                            </form:form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>