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
        <h1>New User</h1>
        <c:url var="create_url" value="/users/create"/>
        <form:form action="${create_url}" method="post" modelAttribute="user">
            <table>
                <tbody>
                    <tr>
                        <td><form:label path="user_name">NAME:</form:label></td>
                        <td><form:input type="text" path="user_name" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="user_surname">SURNAME:</form:label></td>
                        <td><form:input type="text" path="user_surname" maxlength="50" /></td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" value="Insert"/>
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
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.user_id}</td>
                        <td>${user.user_name}</td>
                        <td>${user.user_surname}</td>
                        <td>
                            <c:url var="delete_url" value="/users/delete/${user.user_id}"/>
                            <form:form action="${delete_url}" method="get">
                                <input type="submit" value="Delete"/>
                            </form:form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>