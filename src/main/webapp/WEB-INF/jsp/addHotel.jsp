<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Додати готель</title>
</head>

<body>
<div>
    <form:form method="POST" modelAttribute="hotelForm">
        <h2>Додати новий готель</h2>
        ${hotelidError}
        <div>
            <form:input type="text" path="name" placeholder="Назва"
                        autofocus="true"/>
        </div>
        <div>
            <form:input type="text" path="country" placeholder="Країна"
                        autofocus="true"/>
        </div>
        <div>
            <form:input type="text" path="room_count" placeholder="Кількість кімнат"/>
        </div>
        <div>
            <form:input type="text" path="max_booking_lenght" placeholder="К-сть днів бронювання наперед"/>
        </div>

        <button type="submit">Додати готель</button>
    </form:form>
    <a href="/">Головна</a>
</div>
</body>
</html>