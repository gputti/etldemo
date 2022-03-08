FROM adoptopenjdk:14.0.2_12-jre-hotspot 
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
