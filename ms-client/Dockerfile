FROM maven:3-openjdk-17

WORKDIR /ms-client
COPY . .
RUN mvn clean install -DSkipTests

CMD mvn spring-boot:run