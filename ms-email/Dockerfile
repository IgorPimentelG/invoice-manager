FROM maven:3-openjdk-17

WORKDIR /ms_email
COPY . .
RUN mvn clean install -DskipTests

CMD mvn spring-boot:run
