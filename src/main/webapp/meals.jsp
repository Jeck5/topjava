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
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${mealsWithExceed}" var="meal">
            <tr>
                <td><c:out value="${meal.getDateTime().toString().replace('T',' ')}"/></td>
                <td><c:out value="${meal.getDescription()}"/></td>
                <td><c:out value="${meal.getCalories()}"/></td>
                <td><span style="${meal.isExceed() ? "color: red" : "color: green"}" ><c:out value="${meal.isExceed()}"/></span></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</nav>
</body>
</html>