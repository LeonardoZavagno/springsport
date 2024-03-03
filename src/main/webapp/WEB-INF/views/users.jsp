<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>View Users</title>
    </head>
    <body>
        <h2>New User</h2>
        <c:url var="add_user_url" value="/users/adduser"/>
        <form:form action="${add_user_url}" method="post" modelAttribute="user">
            <table>
                <tbody>
                    <tr>
                        <td><form:label path="user_id">ID:</form:label></td>
                        <td><form:input type="text" path="user_id"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="user_name">NAME:</form:label></td>
                        <td><form:input type="text" path="user_name"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="user_surname">SURNAME:</form:label></td>
                        <td><form:input type="text" path="user_surname"/></td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" value="Insert"/>
        </form:form>
        <br/>
        <br/>
        <h2>List Users</h2>
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
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>