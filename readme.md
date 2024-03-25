Load/create database:
`pg_restore -d testcrud db.dump`

Start application:

`./gradlew build`
`java -jar .\build\libs\crud-0.0.1-SNAPSHOT.jar`

Some examples for API functions:

1. POST `localhost:8080/category/create?title=title`
2. GET `localhost:8080/product/all`
3. DELETE `localhost:8080/product/delete?id=UUID`
4. PUT `localhost:8080/product/update?id=UUID&category_id=UUID`
5. GET `localhost:8080/category/UUID`
