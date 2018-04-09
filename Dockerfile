FROM openjdk:9.0.4-jdk
ADD maven/docker-testdrive.jar docker-testdrive.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "docker-testdrive.jar"]