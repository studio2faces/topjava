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
<p><a href="/topjava/meals/add">Add Meal</a></p>
<p></p>

<style>

    body {
        font-family: Tahoma, serif;
    }

    table {
        width: 700px;
        border-collapse: collapse;
        font-size: 14px;
    }

    table td, table th {
        border: 1px solid #bdbcbc;
        padding: 5px;
        vertical-align: middle;
    }

    form {
        padding: 0;
        margin: 0;
    }

    td button {
        margin: 0 auto;
        display: block;
        font-size: 10px;
        text-transform: uppercase;
        font-weight: 400;

        border-radius: 3px;

        background-color: #a1c99c;
        padding: 7px;
        color: white;
        border: none;
    }

    .button-del {
        background-color: #bf4358;
        border: none;
    }
</style>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${mealsToList}" var="meal">
        <tr style="color: ${meal.excess ? "red" : "green"}">
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <form method="post" action="/topjava/meals">
                    <input type="hidden" name="method" value="update">
                    <input type="hidden" name="id" value=${meal.id}>

                    <button type="submit" name="submit_param" value="submit_value">
                        Update
                    </button>
                </form>
            </td>
            <td>
                <form method="post" action="/topjava/meals">
                    <input type="hidden" name="method" value="delete">
                    <input type="hidden" name="id" value=${meal.id}>
                    <button class="button-del" type="submit" name="submit_param" value="submit_value">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>