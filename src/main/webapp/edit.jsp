<%--
  Created by IntelliJ IDEA.
  User: mb
  Date: 05.10.2020
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create or edit</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Create or edit meal</h2>
<p><a href="/topjava/meals">List of meals</a></p>
<p></p>

<style>
    body {
        font-family: Tahoma;
    }

    input {
        height: 35px;
        padding: 10px;
    }

    button, .button {

        font-size: 10px;
        text-transform: uppercase;
        font-weight: 400;

        border-radius: 3px;

        background-color: #a1c99c;
        padding: 7px;
        color: white;
        border: none;
    }

    .button-can, .button {
        background-color: #bf4358;
        text-decoration: none;
    }
</style>

<p>
    Edit meal - ID <c:out value="${param.id}" default="(new meal)"/>
</p>

<form action="/topjava/meals" method="post">
    <c:if test="${param.id == null}">
        <input type="hidden" name="method" value="create">
    </c:if>
    <c:if test="${param.id != null}">
        <input type="hidden" name="method" value="update-to-edit">
        <input type="hidden" name="id" value="${param.id}">
    </c:if>

    <p>DateTime: <input type="datetime-local" name="date" value="${param.dateTime}" required></p>
    <p>Description: <input type="text" name="description" value="${param.description}" required></p>
    <p>Calories: <input type="text" name="calories" value="${param.calories}" required pattern="^[ 0-9]+$"></p>
    <p>
        <button type="submit">Save</button>
        <a href="/topjava/meals" class="button">Cancel</a>
    </p>
</form>
</body>
</html>