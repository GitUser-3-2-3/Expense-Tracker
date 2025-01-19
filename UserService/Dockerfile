FROM amazoncorretto:17

WORKDIR /build
COPY /target/UserService-0.0.1-SNAPSHOT.jar /build/UserService-1.0.0.jar

EXPOSE 8082

ENV DB_URL=jdbc:mysql://mysql-expense:3306/userservice

CMD java -jar -Dspring.datasource.url=${DB_URL} UserService-1.0.0.jar