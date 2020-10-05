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

<p>
    Edit meal - ID <c:out value="${param.id}" default="(new meal)"/>
</p>

<form action="/topjava/meals" method="post">
    <c:if test="${param.id == null}">
        <input type="hidden" name="method" value="create">
    </c:if>
    <c:if test="${param.id != null}">
        <input type="hidden" name="method" value="update2">
        <input type="hidden" name="id" value="${param.id}">
    </c:if>

    <p>DateTime: <input type="datetime-local" name="date"></p>
    <p>Description: <input type="text" name="description"></p>
    <p>Calories: <input type="text" name="calories"></p>
    <p><input type="submit" value="Save">
        <button>Cancel</button>
    </p>
</form>

</body>
</html>
