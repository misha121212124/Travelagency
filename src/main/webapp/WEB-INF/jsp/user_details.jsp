<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Користувач: ${user.username}</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>

<body>
<div>
    <h3>ID:  ${user.id}</h3>
    <h3>Username: ${user.username}</h3>
    <table>
        <thead>
        <th>Date</th>
        <th>Hotel name</th>
        <th>Room</th>
        <th>Booking ID</th>
        </thead>
        <c:forEach items="${user.bookings}" var="booking">
            <tr>
                <td>${booking.date}</td>
                <td>${booking.hotel.name}</td>
                <td>${booking.room}</td>
                <td>${booking.id}</td>
            </tr>
        </c:forEach>
    </table>

    <a href="/">Головна</a>
</div>
</body>
</html>