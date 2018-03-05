<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <title>MealPage</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
</head>
<body>
<c:url var="addUrl" value="/meals/add" />
<h3><a href="index.html">Home</a></h3>
<h2>Create/edit meal</h2>
<nav>
    <form method="POST" action='meals' name="mealForm">
        Meal ID : <input type="text" readonly="readonly" name="mealid"
                         value="<c:out value="${meal.getId()}" />" /> <br />
        Description : <input
            type="text" name="description"
            value="<c:out value="${meal.getDescription()}" />" /> <br />
        Calories : <input
            type="text" name="calories"
            value="<c:out value="${meal.getCalories()}" />" /> <br />
        DateTime : <input
            type="text" name="datetime"
            value="<c:out value="${meal.getDateTime().toString().replace('T',' ')}" />" /> <br /> <input
            type="submit" value="Submit" />
    </form>
</nav>
</body>
</html>