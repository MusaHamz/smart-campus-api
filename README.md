# Smart Campus Sensor & Room Management API

## Module
5COSC022W Client-Server Architectures

## Coursework Title
Smart Campus Sensor & Room Management API

## Student
Musa Syfullah Hamzah

---

## 1. Project Overview

This project is a RESTful API developed using JAX-RS for the university's Smart Campus initiative. The API allows campus facilities managers and automated systems to manage rooms, sensors, and historical sensor readings.

The system supports:

- Room management
- Sensor registration and filtering
- Linking sensors to rooms
- Nested sensor readings
- Updating a sensor's current value when a new reading is added
- Custom error handling
- Request and response logging

The API uses an in-memory data store with Java collections such as `HashMap`, `ArrayList`, and synchronized collections. No database is used.

---

## 2. Technology Stack

- Java 17
- Maven
- JAX-RS
- Jersey
- Grizzly embedded HTTP server
- JSON using Jackson
- In-memory Java data structures

Spring Boot and database technologies are not used.

---

## 3. API Base URL

```text
http://localhost:8080/api/v1