## About

This is a simple order manager implementation that utilizes the 
following technologies:

* Java 8
* Spring Boot
* Spring Data
* Logback
* PostgreSQL
* Flyway

---

## Getting Started
to build this project, simple run:

### in Linux:
> ./gradlew build && docker-composer up -d

to run it inside a docker container or run 

> ./run-app.sh

to run jar directly.

### in Windows:
> ./gradlew build; docker composer up -d

to run it inside a docker container or run

> ./run-app.bat

to run jar directly.

**In both cases** remember to configure `application.properties` as needed.

---

## Routes

To explore available API routes, access the Swagger-generated API documentation at: 

> http://server_ip:port/api/api-docs

Replace server_ip and port with the appropriate values for your setup.