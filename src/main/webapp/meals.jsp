<%--
  Created by IntelliJ IDEA.
  User: mb
  Date: 03.10.2020
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="edit.jsp">Add Meal</a></p>
<p></p>

<style>
    table {
        width: 700px;
        border-collapse: collapse;
    }

   table td, table th {
        border: 1px solid #000;
        padding: 7px;
    }
</style>

<table>
    <tr>
        <th></th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${mealsToList}" var="meal">
        <tr style="color: ${meal.excess ? "red" : "green"}">
            <td><c:out value="${meal.id}"/></td>
            <td><c:out value="${meal.dateTime}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="index.html">Update</a></td>
            <td><a href="index.html">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
