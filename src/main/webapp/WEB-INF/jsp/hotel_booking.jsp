<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hotel ${hotel.name}</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>

<body>
<div>
    <div>
        <h3>${hotel.name}, ${hotel.country}</h3>
        <a href="/hotel_search">Пошук готелю</a>
        <br>
        <br>

        <form:errors path="booking"/>
        ${bookingError}
    </div>

    <div>
        <form action="${pageContext.request.contextPath}/hotel_booking/{date1}{date2}" method="GET">
            <input name="date1" type="date" placeholder="Початкова дата"
                   autofocus="false" value="${dateList[0]}"/>
            <input name="date2" type="date" placeholder="Кінцева дата"
                   autofocus="false" value="${dateList[1]}"/>
            <input type="hidden" name="hotelId" value="${hotel.id}"/>
            <input type="hidden" name="action" value="getPlaces"/>
            <button type="submit">Шукати місця</button>
        </form>
    </div>
    <br>
    ${searchingResult}

    <div>
        <table>
            <thead>
            <th>Кімната</th>
            <c:forEach items="${(allPlacess.get(0))}" var="room">
                <th>${room.date}</th>
            </c:forEach>
            <th>Кімната</th>
            </thead>
            <tbody id="list">
            <c:forEach items="${allPlacess}" var="room">
                <c:set var="color" scope="page" value="green" />
                <tr>
                    <td>${room.get(0).room}</td>
                    <c:forEach items="${room}" var="booking">
                        <c:set var="color" scope="page" value="green" />
                        <c:if test="${booking.booked}">
                            <c:set var="color" scope="page" value="red" />
                        </c:if>
                        <td bgcolor="${color}">
                            <c:if test="${!booking.booked}">
                                <form action="${pageContext.request.contextPath}/hotel_booking" method="post">
                                    <input type="hidden" name="hotelId" value="${hotel.id}"/>
                                    <input type="hidden" name="room" value="${booking.room}"/>
                                    <input type="hidden" name="date" value="${booking.date}"/>
                                    <input type="hidden" name="date1" value="${dateList.get(0)}"/>
                                    <input type="hidden" name="date2" value="${dateList.get(1)}"/>
                                    <input type="hidden" name="action" value="book"/>
                                    <button type="submit">Book</button>
                                </form>
                            </c:if>
                        </td>
                    </c:forEach>
                    <td>${room.get(0).room}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div>
        <br>
        <form action="${pageContext.request.contextPath}/hotel_search" method="post">
            <input type="hidden" name="hotelId" value="${hotel.id}"/>
            <input type="hidden" name="action" value="showHotel"/>
            <button type="submit">Показати всі</button>
        </form>
        <br>
        <sec:authorize access="hasRole('ROLE_MANAGER')">
            <form action="${pageContext.request.contextPath}/hotel_addRooms" method="post">
                <input name="additionalRooms" type="text" placeholder="Скільки кімнат додати?" autofocus="false"/>
                <input type="hidden" name="hotelId" value="${hotel.id}"/>
                <input type="hidden" name="date1" value="${dateList.get(0)}"/>
                <input type="hidden" name="date2" value="${dateList.get(1)}"/>
                <input type="hidden" name="action" value="addRooms"/>
                <button type="submit">Збільшити кількість кімнат</button>
            </form>
        </sec:authorize>
    </div>

    <a href="/">Головна</a>
</div>
</body>
</html>