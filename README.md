# Todo Application

Jaime Cenzano

This is a note-taking application project developed with Spring Boot. The application allows creating, updating, deleting, and archiving notes, as well as managing categories for the notes.

## Requirements

- Java 17 
- Maven 4.0.0 
- Docker (optional, for building and running the application in a container)

## Configuration

### Application Properties

The application properties are configured in the `src/main/resources/application.properties` file. Make sure to configure the following properties:

```ini
spring.application.name=todo
server.port=8080
spring.jpa.properties.hibernate.default_schema=notesapp
spring.jpa.hibernate.ddl-auto=update
spring.profiles.active=${SPRING_PROFILES_ACTIVE}
spring.datasource.url=${DATABASE_URL}
spring.config.import=optional:secrets.properties
```

## Create  db scheme in postgresql

```sql
CREATE SCHEMA notesapp
```

## Build and Run

### Build with Maven

To build the project, run the following command in the project root:

```shell
mvn clean install
```

### Run the Application

To run the application, use the following command:

```shell
mvn spring-boot:run
```

### Generate the .jar

To package your application with Maven use the following command:

```shell
mvn clean package
```

### Build and Run with Docker

To build the Docker image, run the following command in the project root:

```shell
docker build . -t jpcenzano/noteapp:0.0.2-SNAPSHOT
```

To run the Docker image, use the following command:

```shell
docker run -p 8080:8080 jpcenzano/noteapp:0.0.2-SNAPSHOT
```

Set up the enviroment variables
```
DATABASE_URL=jdbc:postgresql://.....
SPRING_PROFILES_ACTIVE=prd
```

## API Endpoints

### Notes

- **GET /api/notes/**: Retrieves all notes, with the option to filter by archived status.
- **POST /api/notes/**: Saves a new note.
- **PUT /api/notes/{id}**: Updates an existing note.
- **DELETE /api/notes/{id}**: Deletes a note by its ID.
- **PUT /api/notes/{id}/archive**: Archives a note by its ID.
- **PUT /api/notes/{id}/unarchive**: Unarchives a note by its ID.

### Categories

- **PUT /api/notes/{noteId}/categories/name/{categoryName}**: Adds a category to a note by the category name.
- **DELETE /api/notes/{noteId}/categories/name/{categoryName}**: Removes a category from a note by the category name.
- **GET /api/notes/categories/{categoryName}**: Retrieves notes by the category name.
- **GET /api/notes/{noteId}/categories**: Retrieves the categories of a note by its ID.



## References

For more information, please refer to the official documentation and guides:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/maven-plugin)
- [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/maven-plugin/build-image.html)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#using.devtools)
- [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

- [Deploy Spring boot app on render](https://hostingtutorials.dev/blog/free-spring-boot-host-with-render)