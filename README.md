# Kogintive-offers-Api
Kognitive Spring Boot APIs

## Purpose

To demonstrate the usage of the Spring Boot with H2 database and exposed end points.

## What's inside

The project is a working application with a minimal setup. It contains:
* Spring boot application with standard project structure. 
* common plugins and libraries
* docker setup
* swagger configuration for api documentation ( To see swagger document ui with please open http://localhost:8080/swagger-ui.html#)

## Postman collection

We have postman collection too, just navigate to `postman` folder inside the root folder and import to your postman and give it a try.(Oh yeah you have to run application too in your local :P )   


## Plugins

The template contains the following plugins:

* checkstyle

  https://docs.gradle.org/current/userguide/checkstyle_plugin.html

  Performs code style checks on Java source files using Checkstyle and generates reports from these checks.
  The checks are included in gradle's *check* task (you can run them by executing `./gradlew check` command).



* io.spring.dependency-management

  https://github.com/spring-gradle-plugins/dependency-management-plugin

  Provides Maven-like dependency management. Allows you to declare dependency management
  using `dependency 'groupId:artifactId:version'`
  or `dependency group:'group', name:'name', version:version'`.

* org.springframework.boot

  http://projects.spring.io/spring-boot/

  Reduces the amount of work needed to create a Spring application

## Building and deploying the application

### Building the application

The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build the project execute the following command:

```bash
  ./gradlew build
```

