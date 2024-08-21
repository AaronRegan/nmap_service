# NMAP App

## Getting Started

## Running solution locally

In order to run the solution locally, you first need to make sure to meet the following requirements:

- Java 17
- [**Maven 3.x**](https://maven.apache.org/download.cgi)
- [**mySQL**](https://www.mysql.com/downloads/)
- [**NMAP**](https://nmap.org/download)

## Starting the app

From the root folder execute:

### Springboot

**Compile**

`mvn package`

**Start a DB instance**

A docker instance of mySQL has been provided in a docker-compose file, to start this:

`docker compose -f docker-compose.dev.yml up`

**Run Springboot Application**

`mvn spring-boot:run`

## API Documentation

The API Documentation can be accessed under:

* http://localhost:8080/swagger-ui/index.html




