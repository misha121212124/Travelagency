<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Реєстрація</title>
</head>

<body>
<div>
  <form:form method="POST" modelAttribute="userForm">
    <h2>Реєстрація</h2>
    <div>
      <form:input type="text" path="name" placeholder="Ім'я"
                  autofocus="true"/>
      <form:errors path="name"/>
        ${usernameError}
    </div>
    <div>
      <form:input type="password" path="password" placeholder="Пароль"/>
    </div>
    <div>
      <form:input type="password" path="passwordConfirm"
                  placeholder="Підтвердіть ваш пароль"/>
      <form:errors path="password"/>
        ${passwordError}
    </div>
    <button type="submit">Зареєструватися</button>
  </form:form>
  <a href="/">Головна</a>
</div>
</body>
</html>