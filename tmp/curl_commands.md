curl http://localhost:8080/topjava/rest/meals
curl http://localhost:8080/topjava/rest/meals/100003
curl -X "DELETE" http://localhost:8080/topjava/rest/meals/100003
curl -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals/  -d "{\"dateTime\":\"2018-04-23T18:30:00\",\"description\":\"New\",\"calories\":779}"
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100005  -d "{\"dateTime\":\"2015-04-23T18:30:00\",\"description\":\"New\",\"calories\":779}"
curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-31&endTime=19:00"