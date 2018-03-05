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
        <div class="form-group row">
            <label for="id1" class="col-sm-1 col-form-label">Meal ID</label>
            <div class="col-sm-3">
                <input type="text" name="mealid" readonly="readonly" id="id1"
                       value="<c:out value="${meal.getId()}" />" />
            </div>
        </div>
        <div class="form-group row">
            <label for="id2" class="col-sm-1 col-form-label">Description</label>
            <div class="col-sm-3">
                <input type="text" name="description" id="id2"
                       value="<c:out value="${meal.getDescription()}" />" />
            </div>
        </div>
        <div class="form-group row">
            <label for="id3" class="col-sm-1 col-form-label">Calories</label>
            <div class="col-sm-3">
                <input type="text" name="calories" id="id3"
                       value="<c:out value="${meal.getCalories()}" />" />
            </div>
        </div>
        <div class="form-group row">
            <label for="id4" class="col-sm-1 col-form-label">Datetime</label>
            <div class="col-sm-3">
                <input type="text"  name="datetime" id="id4"
                       value="<c:out value="${meal.getDateTime().toString().replace('T',' ')}" />" />
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-3">
                <input type="submit" value="Submit" />
            </div>
        </div>

    </form>


</nav>
</body>
</html>