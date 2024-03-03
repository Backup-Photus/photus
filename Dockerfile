FROM openjdk:20
WORKDIR /app
EXPOSE 8080
COPY target/photus.jar photus.jar
ENTRYPOINT [ "java" ,"-jar","photus.jar" ]

