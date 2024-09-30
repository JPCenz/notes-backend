# Utilizar una imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim-buster

# Argumento para el puerto que se expondrá
ARG PORT=8080

# Exponer el puerto (el mismo que tu aplicación Spring Boot utiliza)
EXPOSE ${PORT}

ENV SPRING_PROFILES_ACTIVE=prod
ENV DATABASE_URL=jdbc:mysql://localhost:3306/todo
# Agregar el archivo jar de tu aplicación al contenedor
ADD target/todo-0.0.2-SNAPSHOT.jar prueba.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/prueba.jar"]