<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
</head>
<body>
<c:url var="addUrl" value="/meals/add" />
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<nav>
    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th class="col-md-1" scope="col">Datetime</th>
            <th class="col-md-1" scope="col">Description</th>
            <th class="col-md-1" scope="col">Calories</th>
            <th class="col-md-1" scope="col">Exceed</th>
            <th class="col-md-1" scope="col">Edit</th>
            <th class="col-md-1" scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${mealsWithExceed}" var="meal">
            <c:url var="editUrl" value="/meals?action=edit&id=${meal.getId()}" />
            <c:url var="deleteUrl" value="/meals?action=delete&id=${meal.getId()}" />
            <tr style="color: ${!meal.exceed ? '#00ff00 ': '#ff0000 '};">
                <td><c:out value="${meal.getDateTime().toString().replace('T',' ')}"/></td>
                <td><c:out value="${meal.getDescription()}"/></td>
                <td><c:out value="${meal.getCalories()}"/></td>
                <%--<td><span style="${meal.isExceed() ? "color: red" : "color: green"}" ><c:out value="${meal.isExceed()}"/></span></td>--%>
                <td><a href="${editUrl}">Edit</a></td>
                <td><a href="${deleteUrl}">Delete</a></td>
            </tr>
        </c:forEach>
        <c:url var="addUrl" value="/meals?action=edit&id=0" />
        <td><a href="${addUrl}">Add new meal</a></td>
        </tbody>
    </table>
</nav>
</body>
</html>