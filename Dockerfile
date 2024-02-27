FROM openjdk:17-jdk
WORKDIR /app
COPY target/customers-0.0.1-SNAPSHOT.jar /app/customers-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "customers-0.0.1-SNAPSHOT.jar"]