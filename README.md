# TaskManager API

## Overview
TaskManager API is a backend application built with Spring Boot that allows users to manage their tasks.

The project demonstrates:
- REST API design
- secure authentication with Spring Security
- flexible filtering logic
- clean layered architecture (Controller → Service → Repository)

Each user can access only their own tasks, ensuring data isolation and security.

---

## Features
- User authentication (Basic Auth via Spring Security)
- Create, update, delete tasks
- Get all tasks for the current authenticated user
- Flexible filtering:
    - by status
    - by keyword
    - combined filters (status + keyword)
- DTO-based data validation and API responses
- Custom exception handling:
    - 400 Bad Request
    - 401 Unauthorized
    - 403 Forbidden
    - 404 Not Found
    - 500 Internal Server Error

---

## Tech Stack
- Java 23
- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- Postman

---

## Architecture
The project follows a clean layered architecture:

Controller → handles HTTP requests  
Service → contains business logic  
Repository → works with database

Additional layers:
- DTO → separates internal models from API responses
- Security → handles authentication and user details
- Exception → custom error handling

---

## Project Structure

```
com.example.taskmanagerapi
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── security
├── service
```

---

## API Endpoints

| Method | Endpoint | Description |
|------|--------|-------------|
| POST | /tasks | Create task |
| GET | /tasks | Get all tasks |
| GET | /tasks?status=NEW | Filter by status |
| GET | /tasks?keyword=task | Filter by keyword |
| GET | /tasks?status=NEW&keyword=task | Combined filter |
| PUT | /tasks/{id} | Update task |
| DELETE | /tasks/{id} | Delete task |

---

## Example Request

```
GET /tasks?keyword=task
```

## Example Response

```json
{
  "id": 1,
  "title": "Buy milk",
  "status": "NEW"
}
```

---

## Security
- Basic Authentication is used
- Each request requires authorization
- Users can only access their own tasks

---

## How to Run

```bash
git clone <your-repo-url>
cd taskmanager-api
mvn spring-boot:run
```

Application will start at:
```
http://localhost:8080
```

---

## Testing
The API was tested using Postman:
- Authentication via Basic Auth
- Filtering logic verification
- Edge cases and error handling

---

## What I Learned
- Building REST APIs with Spring Boot
- Designing layered backend architecture
- Implementing filtering logic with Spring Data JPA
- Working with Spring Security (authentication & authorization)
- Debugging common backend errors (401, 403, 404, 400, 500)

---

## Status
Project is fully functional and tested.

---

## Screenshots

### User Registration
![User Registration](screenshots/register.png)

### Create Task
![Create Task](screenshots/create-task.png)

### Get All Tasks
![Get Tasks](screenshots/get-tasks.png)

### Filter Tasks (by status)
![Filter Tasks](screenshots/filter-status.png)

### Update Task
![Update Task](screenshots/update-task.png)

### Error Handling (400 Bad Request)
![Error 400](screenshots/error-400.png)# task-manager-fullstack
