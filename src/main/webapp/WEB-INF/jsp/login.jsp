<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Увійдіть з вашого акаунту</title>
</head>

<body>
<sec:authorize access="isAuthenticated()">
  <% response.sendRedirect("/"); %>
</sec:authorize>
<div>
  <form method="POST" action="/login">
    <h2>Вхід в систему</h2>
    <div>
      <input name="username" type="text" placeholder="Ім'я"
             autofocus="true"/>
      <input name="password" type="password" placeholder="Пароль"/>
      <button type="submit">Увійти</button>
      <h4><a href="/registration">Зареєструватися</a></h4>
    </div>
  </form>
</div>

</body>
</html>
