# Task Manager (Fullstack)

A fullstack application for managing tasks with search, filtering, and CRUD operations.  
The project demonstrates integration between frontend and backend, as well as clean separation of concerns.

## Technologies

### Backend
- Java
- Spring Boot
- Spring Security
- JPA / Hibernate
- H2 Database

### Frontend
- JavaScript (Vanilla)
- HTML / CSS

## Features

### Task Management
- Create tasks
- Update tasks
- Delete tasks
- View all tasks

### Search and Filtering
- Backend-driven search by keyword
- Dynamic rendering of results
- Clear search functionality
- Notification when no tasks are found

## Implementation Details

- Search is implemented via backend endpoint:
  GET /tasks?keyword=...
- Clear separation of responsibilities:
  - Backend handles data and business logic
  - Frontend handles UI and interaction
- Graceful handling of empty states and errors
- Improved user experience with:
  - Clear search button
  - Toast notifications

## Running the Project

### Backend

1. Run the Spring Boot application
2. Server will be available at:
   http://localhost:8080

### Frontend

1. Open index.html in a browser  
   or use Live Server

## API Endpoints

Get all tasks:
GET /tasks

Search tasks:
GET /tasks?keyword=task

Create task:
POST /tasks

Update task:
PUT /tasks/{id}

Delete task:
DELETE /tasks/{id}

## Project Goal

This project was built to demonstrate:
- Fullstack integration
- REST API usage
- CRUD application design
- UI interaction and state management

## Future Improvements

- User authentication
- Responsive design
- Migration to React
- Filtering by status

## Author

Alla Beloshapka  
Junior Fullstack Developer
