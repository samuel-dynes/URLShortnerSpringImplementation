# URL Shortener Project

A simple and modular URL shortener project built with Spring Boot.

## Table of Contents

- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [API Docs](#API Documentation)
- [Next Steps](#Next Steps)
- [Architecture](#architecture)
  - [Benefits Of This Approach](#benefits-of-this-approach)
  - [Areas Of Improvement](#areas-of-improvement)

## Introduction

This project is designed to provide a URL shortening service with a clear separation of concerns and a modular structure. It leverages Spring Boot for rapid development.

## Getting Started

### Launch via Docker

To run the URL Shortener application using Docker, follow the steps below:

#### Prerequisites
- [Docker](https://www.docker.com/get-started)
- [Maven](https://maven.apache.org/download.cgi) (for building the project)

Make sure you have the appropriate JDK installed. This project requires JDK 21.

##### Build the Maven Project
Open a terminal and navigate to the project directory. Run the following command to build the Maven project:

```bash
mvn clean install
```

#### Build and Run Docker Containers
Once the Maven build is complete, you can use Docker Compose to build and run the Docker containers. Run the following command from the project root:
```bash
docker-compose up --build
```
This command will download the necessary Docker images, build the Spring Boot application, and start the PostgreSQL and Spring Boot containers.

#### Access the Application
After the containers are up and running, you can access the URL Shortener application at http://localhost:8080.

To add a url to the db and receive the link to expand it use the following command
```bash
curl -X POST --data-urlencode "<URL_TO_SHORTEN>" http://localhost:8080/urlapi/1.0/shorten
```

To retrieve the lengthened URL you can use the following command 
```bash
curl http://localhost:8080/urlapi/1.0/expand/<SHORTENED_URL>
```
#### Stop the application 
To stop the Docker containers, open a new terminal window and run:
```bash
docker-compose down
```


### Launch locally for debugging
1. Navigate to `src/main/resources/local/database/local-compose.yml`
2. Run Command ```docker compose -f local-compose.yml up```
3. Run Main Application Class (`UrlShortnerApplication`) in chosen IDE

## API Documentation

Explore and interact with the API using Swagger, while the application is running you can view the documentation at the following links:
- [Swagger JSON](http://localhost:8080/v2/api-docs)
- [Swagger UI](http://localhost:8080/swagger-ui.html)

### Exploring the API

- Click on the [Swagger UI](http://localhost:8080/swagger-ui.html) link to access the interactive API documentation.
- Use Swagger UI to make requests, view responses, and explore available endpoints.


# Nothing past this point would be in an actual readme.
But considering there was an architectural element I thought may as well give high level reasoning, known issues and next steps

## Next Steps

### Cloud deployment
Given that im a single dev in this scenario and im looking to capitalise on a time sensitive issue, I wouldn't be 
implementing a full pipeline, id be deploying the MVP manually and using the data I get from that to inform the build pipeline
but my next steps would be 


#### Deploy Postgress DB 

1. Host postgres database instance on RDS:
   - Ensure that security groups allows access to the db from a future EC2 instance
   - Update application.yaml to reflect the RDS database URL, username, password and ports

#### Deploy Spring Application

1. Get a domain from goDaddy, lets run with "http://shorturl.com/"

2. Host the spring application on an EC2 instance:
   - Probably use a m7g.medium due to the mixed resource requirements + to judge demand
   - Will need to install java on the instance + ensure that 8080 is allowed in the security group
   - Copy built jar to EC2 Instance and run it, confirm that database connections are possible.

3. Ensure http://shorturl.com/ directs to the public IP of the spring application instance


#### Add basic UI
Adding a basic templated site will be a fine start to begin with, a tool like [ThymeLeaf](https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html#introducing-thymeleaf)
will allow us to have basic templates per endpoint   

## Architecture

The project follows a layered architecture with clear separation of concerns:

1. **Controller Layer (`ShortenedUrlController`):**
    - Handles HTTP requests and orchestrates data flow.
    - Translates between DTOs and domain entities.

2. **Service Layer (`UrlShortenerService`):**
    - Encapsulates business logic for URL shortening and expansion.
    - Independent of data access mechanism.

3. **Component (`ShortenedUrlComponent`):**
    - Generates shortened URLs based on configuration settings.
    - Promotes modularity by separating URL generation logic.

4. **DTOs (`ShortenedUrlRequestDto`, `ShortenedUrlResponseDto`):**
    - Data transfer objects for clear communication between layers.
    - Realistically this is over-engineering for the simplicity of what we are dealing with, however added them in case of future expansion

5. **Entity (`ShortenedUrlEntity`):**
    - Represents a shortened URL in the database.
    - Annotated for easy integration with JPA.
    - Specifies "urls" as the name of the table to which this entity is mapped
    - Primary key is generated using a table to generate unique identifiers

6. **Repository (`ShortenedUrlRepository`):**
    - Handles data access operations related to `ShortenedUrlEntity`.
    - Extends Spring Data JPA's `JpaRepository`.

7. **Configuration (`ShortenedUrlConfiguration`):**
    - Holds properties related to URL shortening.
    - Annotated with Spring's `@ConfigurationProperties`to bind properties from the application configuration to the corresponding fields in the class. This allows for a centralized and type-safe approach to manage configuration settings related to URL shortening.

8. **Main Application Class (`UrlShortnerApplication`):**
    - Bootstraps the Spring Boot application.
    - Includes annotations for configuration and component scanning.

9. **Spring Boot Configuration (`application.yml`):**
   - Externalized configuration mechanism provided by Spring Boot, allowing you to configure the application without modifying the source code
   - This application YAML focuses on 3 main areas for configuration 
     - Server settings: Settings pertaining to the Spring webapp
     - Database configuration: Settings pertaining to the postgres DB we will be using
     - Shortened URL configuration: Settings pertaining to how shortened URL's will be generated

### Benefits of this approach

- **Modularity:** The project is organized into modular components, making it easier to understand, maintain, and extend.

- **Separation of Concerns:** Each layer has a specific responsibility, promoting a clean separation of concerns and facilitating unit testing.

- **Flexibility:** The modular architecture allows for easy replacement or extension of components without affecting the entire system, only dependant components.

- **Scalability:** The clear separation of layers and concerns enables scalability by allowing individual components to scale independently.

- **Maintainability:** The project follows best practices, making it easier to maintain, refactor, and add new features.

- **Testability:** The use of dependency injection and separation of concerns enhances the testability of individual components through unit tests.


## Areas of improvement
1. **Exception Handling:**
-  Adding proper catching of errors along with more meaningful messages would be a requirement before going customer facing
2. **Logging Standardisation**
- Using sl4j or another framework to log interactions (particularly reads and writes) would be an improvement to aid in debugging if issues were encountered
3. **Security** 
- Implement input validation to ensure that only valid data is processed, makes it less likely for malicious requests to be processed
- Implement HTTPS as the only valid means of access to the application. Whilst we are not processing any confidential information we still want to avoid modification, snooping and having most browsers complain.
4. **CORS configuration**
- Depending on our frontend ends up being developed we may want to configure Cross-Origin Resource Sharing to allow that to function
5. **Caching** 
- The point of the variable to track the ammount of times a given URL is accessed is to allow us to have a data driven approach to general caching.
