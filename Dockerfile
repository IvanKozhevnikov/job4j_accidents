FROM maven:3.6.3-openjdk-17

RUN mkdir job4j_accidents

WORKDIR job4j_accidents

COPY COPY rest-service-1.0.jar /document-service/rest-service-1.0.jar/

RUN mvn package -Dmaven.test.skip=true

CMD ["mvn", "liquibase:update", "-Pdocker"]

CMD ["java", "-jar", "target/accidents.jar"]