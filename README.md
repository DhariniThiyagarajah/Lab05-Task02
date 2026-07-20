# In24-S3-IN2201 - Software Engineering

**Name:** Thyagrajah Dharini  
**Student Registration Number:** 245020P  

---

## Task Summary

This project implements a complete CRUD (Create, Read, Update, Delete) REST API using **Spring Boot 3.4.2** and **Java 21/25**, managed by **Apache Maven**. The domain chosen is **Book**. 

The application utilizes a clean, layered architecture consisting of:
1. **Model (`Book.java`)**: Represents the database table `books` with properties: `id`, `title`, `author`, `isbn`, and `price`.
2. **Repository (`BookRepository.java`)**: Extends `JpaRepository` to perform database queries automatically via Spring Data JPA.
3. **Service (`BookService.java`)**: Holds the business logic, calling the repository to save, fetch, update, and delete entities, and throwing exception when needed.
4. **Controller (`BookController.java`)**: Exposes REST endpoints under `/api/books` and handles HTTP requests and maps responses with appropriate status codes (e.g. 201 Created, 200 OK, 404 Not Found).
5. **Exceptions (`ResourceNotFoundException.java`)**: Custom exception that is annotated with `@ResponseStatus(value = HttpStatus.NOT_FOUND)` to return a standard 404 response.

An in-memory **H2 database** is utilized to store data, with full SQL logging enabled. Integration tests are included in `BookControllerTest.java` to verify all REST endpoints using `MockMvc`.

---

## REST API Endpoints

The API exposes the following endpoints:

| Method | Endpoint | Request Body | Response Status | Description |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/api/books` | Book JSON object | `201 Created` | Create a new book record |
| **GET** | `/api/books` | None | `200 OK` | Retrieve all book records |
| **GET** | `/api/books/{id}` | None | `200 OK` or `404 Not Found` | Retrieve a single book by identifier |
| **PUT** | `/api/books/{id}` | Book JSON object | `200 OK` or `404 Not Found` | Update an existing book record |
| **DELETE** | `/api/books/{id}` | None | `200 OK` or `404 Not Found` | Delete a book record by identifier |

---

## Build and Run Instructions

### Prerequisites
* **Java**: JDK 17, 21, or 25 installed and configured.
* **Maven**: Apache Maven 3.8+ installed (or use the provided `mvnw` wrapper).

### Build and Run Commands
Open a terminal in the root of the project directory and execute the following commands:

```bash
# 1. Clean the project target directory
mvn clean

# 2. Compile the source code
mvn compile

# 3. Run the unit and integration tests
mvn test

# 4. Package the application into a JAR file
mvn package

# 5. Install the package into the local Maven repository
mvn install

# 6. Start the Spring Boot application
mvn spring-boot:run
```

---

## Observations on Maven Commands

Here is the behavior and output observed during the execution of each Maven command:

### 1. `mvn clean`
* **Purpose**: Deletes the `target` directory and all compiled class files or packaged archives from previous builds to start fresh.
* **Observed Behavior**: The command completed successfully in under 2 seconds. The `target` directory was successfully removed from the workspace.
* **Key Output**:
  ```text
  [INFO] --- maven-clean-plugin:3.4.0:clean (default-clean) @ bookapi ---
  [INFO] Deleting D:\245020P_SE_Lab05_Task 02\target
  [INFO] BUILD SUCCESS
  ```

### 2. `mvn compile`
* **Purpose**: Compiles the application source files (`src/main/java/**/*.java`) and copies resources (like `application.properties`) into the `target/classes` folder.
* **Observed Behavior**: Compiles the source files using the configured JDK release target and places the generated `.class` files in the target folder.
* **Key Output**:
  ```text
  [INFO] --- maven-resources-plugin:3.3.1:resources (default-resources) @ bookapi ---
  [INFO] Copying 1 resource from src\main\resources to target\classes
  [INFO] --- maven-compiler-plugin:3.13.0:compile (default-compile) @ bookapi ---
  [INFO] Compiling 6 source files to target\classes
  [INFO] BUILD SUCCESS
  ```

### 3. `mvn test`
* **Purpose**: Compiles test classes and runs the unit and integration tests in the project (using the Surefire plugin).
* **Observed Behavior**: Compiles `BookControllerTest.java` and `BookapiApplicationTests.java`, boots up a lightweight application context, and runs all test cases. Since Java 25 was in use, Mockito's Byte Buddy dependency was run with the experimental flag `-Dnet.bytebuddy.experimental=true` inside Surefire configurations. All 6 tests passed successfully.
* **Key Output**:
  ```text
  [INFO] Running com.example.bookapi.BookapiApplicationTests
  [INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
  [INFO] Running com.example.bookapi.controller.BookControllerTest
  [INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
  [INFO] BUILD SUCCESS
  ```

### 4. `mvn package`
* **Purpose**: Runs compilation, tests, and packs the compiled classes and resources into a distributable JAR file in the `target` directory.
* **Observed Behavior**: The plugin packaged the classes into a JAR file, and then the Spring Boot Maven plugin repackaged it into an executable JAR.
* **Key Output**:
  ```text
  [INFO] Replacing main artifact D:\245020P_SE_Lab05_Task 02\target\bookapi-0.0.1-SNAPSHOT.jar with repackaged archive...
  [INFO] BUILD SUCCESS
  ```

### 5. `mvn install`
* **Purpose**: Packages the project and installs the packaged JAR file into the local Maven repository (usually `~/.m2/repository`), making it usable by other local projects.
* **Observed Behavior**: Copied the POM and JAR artifact into `C:\Users\ASUS\.m2\repository\com\example\bookapi\0.0.1-SNAPSHOT\`.
* **Key Output**:
  ```text
  [INFO] Installing D:\245020P_SE_Lab05_Task 02\target\bookapi-0.0.1-SNAPSHOT.jar to C:\Users\ASUS\.m2\repository\com\example\bookapi\0.0.1-SNAPSHOT\bookapi-0.0.1-SNAPSHOT.jar
  [INFO] BUILD SUCCESS
  ```

### 6. `mvn spring-boot:run`
* **Purpose**: Starts the Spring Boot application using the embedded Apache Tomcat servlet container.
* **Observed Behavior**: Boots up the application context, creates the H2 schema, outputs the Spring logo, and starts Tomcat on port 8080.
* **Key Output**:
  ```text
  2026-07-20T23:10:09.495+05:30  INFO 10092 --- [bookapi] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
  2026-07-20T23:10:09.502+05:30  INFO 10092 --- [bookapi] [           main] com.example.bookapi.BookapiApplication   : Started BookapiApplication in 2.715 seconds
  ```
