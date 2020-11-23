# centene-enrollee-service

### Swagger
http://localhost:8080/swagger-ui.html

to run the fat jar locally with prod profile.
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

```

Before you do that.. make sure to Create db tables for enrollee, dependent.
```
CREATE ROLE centene WITH LOGIN PASSWORD 'kforce'
ALTER ROLE centene CREATEDB;
CREATE DATABASE enrollee_management_service;

connecting via user and to the db.
psql postgres -U centene;
\c enrollee_management_service

```
the following property is set to update, so if there's a change in Entity classes hibernate will update tables for us.
```
  jpa:
    hibernate:
      ddl-auto: update
```


#### TODO:
- Service layer test cases are due..
- REST Layer service cases are not complete.
- Dockerfile.

