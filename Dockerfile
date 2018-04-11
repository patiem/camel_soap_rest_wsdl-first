FROM openjdk:8u162-jre
ADD target/docker-testdrive.jar docker-testdrive.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "docker-testdrive.jar"]