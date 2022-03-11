FROM maven:3.8.1-adoptopenjdk-11
WORKDIR /src/app
COPY target/hola-app-0.0.1-SNAPSHOT.jar .   
EXPOSE 9020
# Corremos el archivo JAR
CMD ["java", "-jar", "hola-app-0.0.1-SNAPSHOT.jar"]