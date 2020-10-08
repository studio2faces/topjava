<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="${pageContext.request.contextPath}/meals/add">Add Meal</a></p>
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
            <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>
                <fmt:formatDate value="${parsedDate}" pattern="yyyy:MM:dd HH:mm"/>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <form method="get" action="${pageContext.request.contextPath}/meals">
                    <input type="hidden" name="method" value="update">
                    <input type="hidden" name="id" value=${meal.id}>
                    <button type="submit">
                        Update
                    </button>
                </form>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/meals">
                    <input type="hidden" name="method" value="delete">
                    <input type="hidden" name="id" value=${meal.id}>
                    <button class="button-del" type="submit">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>