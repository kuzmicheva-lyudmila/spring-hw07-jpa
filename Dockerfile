FROM gradle:6.5.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
EXPOSE 8080
WORKDIR /usr/local/spring-library
COPY --from=build /home/gradle/src/build/libs/*.jar /usr/local/spring-library/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]
