FROM eclipse-temurin:17

#RUN mkdir -p /build
#RUN mkdir -p /build/logs

#WORKDIR /build
#COPY pom.xml /build

#RUN mvn dependency:resolve && mvn compile

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]