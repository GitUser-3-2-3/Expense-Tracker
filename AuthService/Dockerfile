FROM amazoncorretto:17

WORKDIR /build
COPY /target/AuthService-0.0.1-SNAPSHOT.jar /build/AuthService-1.0.0.jar

EXPOSE 8081

ENV DB_URL=jdbc:mysql://mysql-expense:3306/authservice

CMD java -jar -Dspring.datasource.url=${DB_URL} AuthService-1.0.0.jar