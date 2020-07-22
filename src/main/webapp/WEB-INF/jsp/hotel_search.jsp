<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Пошук готелю</title>
</head>
<body>
<div>
    <h2>Пошук готелю</h2>
    <sec:authorize access="hasRole('ROLE_MANAGER')">
        <a href="/addHotel"><h2>Додати готель</h2></a>
    </sec:authorize>
    <form method="GET" action="/hotel_search/{country}">
        <div>
            <input name="country" type="text" placeholder="Яка країна?"
                   autofocus="true"/>
            <button type="submit">Знайти готелі</button>
        </div>
    </form>

    <table>
        <thead>
        <th>Назва</th>
        <th>Країна</th>
        <th>Кількість кімнат</th>
        </thead>

        <c:forEach items="${allHotels}" var="hotel">
            <tr>
                <td>${hotel.name}</td>
                <td>${hotel.country}</td>
                <td>${hotel.room_count}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/hotel_search" method="post">
                        <input type="hidden" name="hotelId" value="${hotel.id}"/>
                        <input type="hidden" name="action" value="showHotel"/>
                        <button type="submit">Перейти</button>
                    </form>
                </td>
                <td>
                    <sec:authorize access="hasRole('ROLE_MANAGER')">
                        <form action="${pageContext.request.contextPath}/hotel_search" method="post">
                            <input type="hidden" name="hotelId" value="${hotel.id}"/>
                            <input type="hidden" name="action" value="deleteHotel"/>
                            <button type="submit">Видалити</button>
                        </form>
                    </sec:authorize>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="/">Головна</a>
</div>
</body>
</html>