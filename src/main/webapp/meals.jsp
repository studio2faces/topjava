<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        .filter {
            width: 800px;
            margin-top: 15px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>

    <form action="meals" method="get">
        <input type="hidden" name="action" value="filter">
        <table class="filter">
            <tr>
                <td>От даты (включая)</td>
                <td>До даты (включая)</td>
                <td>От времени (включая)</td>
                <td>До времени (исключая)</td>
            </tr>
            <tr>
                <td><input type="date" value="${param.get("fromDate")}" name="fromDate"></td>
                <td><input type="date" value="${param.get("toDate")}" name="toDate"></td>
                <td><input type="time" value="${param.get("fromTime")}" name="fromTime"></td>
                <td><input type="time" value="${param.get("toTime")}" name="toTime"></td>
            </tr>
        </table>
        <button type="button"><a href="meals">Cancel</a></button>
        <button type="submit">Filter table</button>
    </form>


    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>U${meal.userId} ${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>