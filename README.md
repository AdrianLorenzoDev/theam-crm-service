# theam-crm-service
## Rest API to manage customer data for a small shop.
Built with [Spring Boot](https://spring.io) and [Gradle](https://gradle.org).

### Want to run the service? 
Requirements:
- [Gradle 4+](https://gradle.org)
- [JDK 1.8](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

Now, run the following command to **build and run**:
```
./gradlew build && java -jar build/libs/gs-spring-boot-0.1.0.jar
```

If it is already built, you can use the **Spring Boot Gradle plugin** to run the app:
```
gradle bootRun
```

### Main objectives
- [x] Customer domain definition, service, repository and rest controller
- [x] Image storage service and rest controller
- [ ] Users domain definition, service, repository and rest controller
- [ ] RestAPI authentication and authorization
- [ ] Service containerization
- [ ] More testing

### Secundary objectives
- [ ] OAuth 2 authentication protocol integration