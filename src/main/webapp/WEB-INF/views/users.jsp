<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>View Users</title>
    </head>
    <body>
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