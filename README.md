# Smart Campus Sensor & Room Management API

## Module
5COSC022W Client-Server Architectures

## Coursework Title
Smart Campus Sensor & Room Management API

## Student
Musa Syfullah Hamzah

## GitHub Repository
https://github.com/MusaHamz/smart-campus-api

---

# 1. Project Overview

This project is a RESTful API developed using JAX-RS for the university's Smart Campus initiative. The API allows campus facilities managers and automated building systems to manage rooms, sensors, and historical sensor readings.

The API supports:

- Room management
- Sensor registration
- Linking sensors to existing rooms
- Filtering sensors by type
- Nested sensor readings
- Updating a sensor's current value when a new reading is added
- Custom JSON error handling
- Request and response logging

The application uses an in-memory data store with Java collections such as `HashMap`, `ArrayList`, and synchronized maps/lists. No database technology is used.

---

# 2. Technology Stack

- Java 17
- Maven
- JAX-RS
- Jersey
- Grizzly embedded HTTP server
- Jackson JSON support
- Java in-memory data structures

This project does not use Spring Boot and does not use a database.

---

# 3. API Base URL

```text
http://localhost:8080/api/v1
```

---

# 4. How to Build and Run the Project

## Prerequisites

Before running the project, make sure these are installed:

- Java JDK 17 or above
- Apache Maven
- Postman for API testing

## Build the project

From the project root folder, run:

```bash
mvn clean compile
```

## Run the server

```bash
mvn exec:java
```

When the server starts successfully, the terminal should show:

```text
Smart Campus API is running at: http://localhost:8080/api/v1/
```

To stop the server, press Enter in the terminal.

---

# 5. API Endpoints

## Discovery Endpoint

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/` | Returns API metadata and resource links |

## Room Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/rooms` | Returns all rooms |
| POST | `/api/v1/rooms` | Creates a new room |
| GET | `/api/v1/rooms/{roomId}` | Returns a specific room |
| DELETE | `/api/v1/rooms/{roomId}` | Deletes a room if it has no sensors assigned |

## Sensor Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/sensors` | Returns all sensors |
| GET | `/api/v1/sensors?type=CO2` | Filters sensors by type |
| POST | `/api/v1/sensors` | Creates a new sensor linked to an existing room |

## Sensor Reading Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/sensors/{sensorId}/readings` | Returns readings for a specific sensor |
| POST | `/api/v1/sensors/{sensorId}/readings` | Adds a new reading and updates the sensor's current value |

---

# 6. Sample curl Commands

## 1. Discovery endpoint

```bash
curl.exe -X GET http://localhost:8080/api/v1/
```

## 2. Get all rooms

```bash
curl.exe -X GET http://localhost:8080/api/v1/rooms
```

## 3. Get one room

```bash
curl.exe -X GET http://localhost:8080/api/v1/rooms/LIB-301
```

## 4. Create a new room

```bash
curl.exe -X POST http://localhost:8080/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"ENG-101\",\"name\":\"Engineering Lab 101\",\"capacity\":30}"
```

## 5. Delete a room

```bash
curl.exe -X DELETE http://localhost:8080/api/v1/rooms/ENG-101
```

## 6. Try to delete a room that still has sensors

```bash
curl.exe -X DELETE http://localhost:8080/api/v1/rooms/LIB-301
```

Expected result: `409 Conflict`.

## 7. Get all sensors

```bash
curl.exe -X GET http://localhost:8080/api/v1/sensors
```

## 8. Filter sensors by type

```bash
curl.exe -X GET "http://localhost:8080/api/v1/sensors?type=CO2"
```

## 9. Create a valid sensor

```bash
curl.exe -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"CO2-001\",\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":420.0,\"roomId\":\"LIB-301\"}"
```

## 10. Create a sensor with an invalid room ID

```bash
curl.exe -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"CO2-999\",\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":500.0,\"roomId\":\"ROOM-DOES-NOT-EXIST\"}"
```

Expected result: `422 Unprocessable Entity`.

## 11. Get readings for a sensor

```bash
curl.exe -X GET http://localhost:8080/api/v1/sensors/TEMP-001/readings
```

## 12. Add a new sensor reading

```bash
curl.exe -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{\"value\":23.4}"
```

---

# 7. Example JSON Request Bodies

## Create Room

```json
{
  "id": "ENG-101",
  "name": "Engineering Lab 101",
  "capacity": 30
}
```

## Create Sensor

```json
{
  "id": "CO2-001",
  "type": "CO2",
  "status": "ACTIVE",
  "currentValue": 420.0,
  "roomId": "LIB-301"
}
```

## Create Sensor Reading

```json
{
  "value": 23.4
}
```

---

# 8. Error Handling

The API uses custom exception mappers to return meaningful JSON responses instead of raw Java stack traces.

| Scenario | HTTP Status | Custom Exception |
|---|---|---|
| Deleting a room that still has sensors | 409 Conflict | `RoomNotEmptyException` |
| Creating a sensor with a missing linked room | 422 Unprocessable Entity | `LinkedResourceNotFoundException` |
| Posting a reading to a maintenance sensor | 403 Forbidden | `SensorUnavailableException` |
| Unexpected runtime error | 500 Internal Server Error | `ExceptionMapper<Throwable>` |

Example 409 response:

```json
{
  "roomId": "LIB-301",
  "sensorCount": 2,
  "error": "Room cannot be deleted because it still has sensors assigned"
}
```

Example 422 response:

```json
{
  "resourceType": "room",
  "resourceId": "ROOM-DOES-NOT-EXIST",
  "error": "Referenced resource does not exist"
}
```

Example 403 response:

```json
{
  "sensorId": "TEMP-M1",
  "error": "Sensor is in MAINTENANCE and cannot accept readings"
}
```

---

# 9. Logging

The API includes a custom logging filter using:

- `ContainerRequestFilter`
- `ContainerResponseFilter`
- `java.util.logging.Logger`

For every request and response, the API logs:

- HTTP method
- Request URI
- Response status code

Example terminal output:

```text
Incoming request: POST http://localhost:8080/api/v1/sensors
Outgoing response: status=201
```

This improves observability because requests and responses can be monitored without placing repeated logging statements inside every resource method.

---

# 10. Project Structure

```text
smart-campus-api/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    └── main/
        └── java/
            └── com/
                └── musa/
                    └── smartcampus/
                        ├── Main.java
                        ├── SmartCampusApplication.java
                        ├── exception/
                        │   ├── LinkedResourceNotFoundException.java
                        │   ├── RoomNotEmptyException.java
                        │   └── SensorUnavailableException.java
                        ├── filter/
                        │   └── ApiLoggingFilter.java
                        ├── mapper/
                        │   ├── GenericExceptionMapper.java
                        │   ├── LinkedResourceNotFoundExceptionMapper.java
                        │   ├── RoomNotEmptyExceptionMapper.java
                        │   └── SensorUnavailableExceptionMapper.java
                        ├── model/
                        │   ├── Room.java
                        │   ├── Sensor.java
                        │   └── SensorReading.java
                        ├── resource/
                        │   ├── DiscoveryResource.java
                        │   ├── RoomResource.java
                        │   ├── SensorResource.java
                        │   └── SensorReadingResource.java
                        └── store/
                            └── DataStore.java
```

---

# 11. Conceptual Report Answers

## Part 1: Service Architecture and Setup

### Question 1: What is the default lifecycle of a JAX-RS resource class?

By default, JAX-RS resource classes normally use a per-request lifecycle. This means that a new resource class instance is created for each incoming HTTP request. This helps keep resource classes simple because instance variables are not usually shared between requests.

However, this also means that data should not be stored only in normal instance variables of a resource class if it needs to remain available across multiple requests. In this project, shared in-memory data is stored in a separate `DataStore` class using static maps and lists.

Because multiple requests may access the same shared collections, synchronized collections are used to reduce the risk of race conditions and inconsistent data. This is important because the application does not use a database, so the in-memory collections act as the main storage for rooms, sensors, and readings.

### Question 2: Why is hypermedia/HATEOAS useful in RESTful design?

Hypermedia is useful because it allows API responses to include links that guide clients to related resources. In this project, the discovery endpoint returns links to the main resource collections, such as rooms and sensors.

This makes the API more self-descriptive. Client developers can discover available resources from the API response instead of relying only on external documentation. Compared to static documentation, hypermedia can make an API easier to navigate and more flexible if resource URLs change in the future.

---

## Part 2: Room Management

### Question 1: What are the implications of returning only IDs versus full room objects?

Returning only room IDs reduces the size of the response and saves network bandwidth. This can be useful when there are many rooms or when the client only needs a lightweight list of identifiers.

Returning full room objects gives the client more useful information in a single request, such as the room name, capacity, and assigned sensor IDs. This reduces the need for the client to make extra requests for each room. In this coursework API, returning full room objects is suitable because the dataset is small and it makes the API easier to test and understand.

### Question 2: Is the DELETE operation idempotent in this implementation?

The DELETE operation is idempotent in terms of server state. If a room with no sensors is deleted, sending the same DELETE request again does not delete anything else or cause further changes to the system.

In this implementation, the first DELETE request may return a success message, while later DELETE requests for the same room may return `404 Not Found` because the room no longer exists. Even though the response may be different, the server state remains the same after the first deletion. Therefore, the DELETE operation is still idempotent.

---

## Part 3: Sensor Operations and Linking

### Question 1: What happens if a client sends a different format when `@Consumes(MediaType.APPLICATION_JSON)` is used?

When a method uses `@Consumes(MediaType.APPLICATION_JSON)`, it expects the request body to be sent as JSON. If a client sends data using a different content type, such as `text/plain` or `application/xml`, the request does not match the media type accepted by the method.

JAX-RS will normally reject the request and return `415 Unsupported Media Type`. This prevents the API from trying to process data in a format that the method is not designed to handle.

### Question 2: Why is `@QueryParam` better than putting the filter value in the path?

Using a query parameter such as `/api/v1/sensors?type=CO2` is better for filtering because the client is still requesting the sensors collection, but with an optional condition applied.

A path such as `/api/v1/sensors/type/CO2` makes the filter look like a fixed resource path. Query parameters are more flexible for searching and filtering because more optional filters could be added later, such as status or room ID. Path parameters are better for identifying a specific resource, such as `/api/v1/sensors/TEMP-001`.

---

## Part 4: Deep Nesting with Sub-Resources

### Question: What are the architectural benefits of the sub-resource locator pattern?

The sub-resource locator pattern helps organise nested resources by delegating responsibility to separate classes. In this project, `SensorResource` manages the main sensor collection, while `SensorReadingResource` manages readings for a specific sensor.

This improves code structure because all reading-related logic is kept in its own class instead of putting every nested path into one large resource class. It makes the code easier to maintain, easier to test, and easier to expand in the future.

The nested path `/api/v1/sensors/{sensorId}/readings` also reflects the real relationship between sensors and readings. Readings belong to a specific sensor, so the URL structure is logical and resource-based.

---

## Part 5: Advanced Error Handling, Exception Mapping and Logging

### Question 1: Why is HTTP 422 more semantically accurate than 404 when the issue is a missing reference inside a valid JSON payload?

HTTP 422 Unprocessable Entity is appropriate when the request body is valid JSON, but the server cannot process it because the data inside is semantically incorrect. In this project, a sensor creation request may contain valid JSON, but the `roomId` may refer to a room that does not exist.

A 404 error usually means the requested URL itself was not found. In this case, the URL `/api/v1/sensors` exists, but the linked room inside the payload is invalid. Therefore, 422 gives a more accurate explanation of the problem.

### Question 2: What are the cybersecurity risks of exposing internal Java stack traces?

Exposing Java stack traces is dangerous because they can reveal internal details about the application. An attacker may learn class names, package names, method names, server file paths, framework details, or implementation structure.

This information could help an attacker identify weaknesses, target specific libraries, or understand how the application is built. For this reason, the API uses a catch-all exception mapper to return a generic `500 Internal Server Error` message instead of exposing raw stack traces to the client.

### Question 3: Why use JAX-RS filters for logging instead of adding `Logger.info()` statements inside every resource method?

JAX-RS filters are useful for logging because logging is a cross-cutting concern. It applies to every request and response, not just one endpoint.

Using a `ContainerRequestFilter` and `ContainerResponseFilter` keeps the resource methods cleaner and avoids repeating the same logging code in every method. This improves maintainability because the logging behaviour can be changed in one place instead of editing every resource class.

---

# 12. Video Demonstration Plan

The video demonstration will be recorded using Postman. The video will show the API running and tested through the following requests:

1. `GET /api/v1/` to show the discovery endpoint.
2. `GET /api/v1/rooms` to show all rooms.
3. `POST /api/v1/rooms` to create a new room.
4. `GET /api/v1/rooms/{roomId}` to retrieve a specific room.
5. `DELETE /api/v1/rooms/LIB-301` to show the 409 conflict rule when a room still has sensors.
6. `GET /api/v1/sensors` to show all sensors.
7. `GET /api/v1/sensors?type=CO2` to show filtering with a query parameter.
8. `POST /api/v1/sensors` to create a valid sensor.
9. `POST /api/v1/sensors` with an invalid room ID to show 422 error handling.
10. `GET /api/v1/sensors/TEMP-001/readings` to show readings for a sensor.
11. `POST /api/v1/sensors/TEMP-001/readings` to add a new reading.
12. `GET /api/v1/sensors` to show that the parent sensor's `currentValue` has updated.
13. `POST /api/v1/sensors/TEMP-M1/readings` to show 403 when the sensor is in maintenance.
14. Terminal output to show request and response logging.

The video demonstration will be uploaded directly to Blackboard as required.

---

# 13. Final Submission Notes

The final submission will include:

- The public GitHub repository link
- The README.md report and instructions
- The Postman video demonstration uploaded to Blackboard

GitHub repository:

```text
https://github.com/MusaHamz/smart-campus-api
```