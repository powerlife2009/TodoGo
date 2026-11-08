# Overview

TodoGo is my training project, the purpose of which is to learn
with the Spring Framework.<br>
It's built on a stack:<br>
   - Spring Boot 3.5 and Java 17,<br>
   - Spring Data JPA,<br>
   - Spring Security 6,<br>
   - Thymeleaf 3,<br>
   - PostgreSQL,<br>
   - and UIKit 3 for FrontEnd.


# Features

The current version of the TodoGo 0.9 project offers the following features:<br>
   - easy creation<br>
   - easy editing<br>
   - deleting
   - status bar
   - task typing<br>
   - sorting by groups, priority, time<br>
   - filtering by groups, priority<br>
   - quick display of up to 5  nearest events<br>
   - quick task search<br>
   - feedback form<br>
   - administration<br>

# Tests

Run the automated tests with Java 17 and `./gradlew test` (`gradlew.bat test`
on Windows). Repository tests use an
in-memory H2 database, so they do not modify the local PostgreSQL database.

Build the executable JAR with `./gradlew bootJar` or start the application with
`./gradlew bootRun`. Local database settings are in `src/main/resources/application.yml`.

! update on 22.04.2021:<br>
   - __Now TodoGo displays beautifully on mobile devices__

# Conclusion

Development continues<br>
Feedback is welcome!


