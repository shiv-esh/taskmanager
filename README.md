# Task Manager API

A Spring Boot–based RESTful application for managing tasks efficiently.  
It provides endpoints for creating, updating, retrieving, and deleting tasks, with features like pagination, status-based filtering, and static token–based authentication for secured access.

---

## Features

- **Task Management:** Create, update, delete, and view tasks.  
- **Pagination Support:** Retrieve tasks in pages for scalability.  
- **Status Filtering:** Fetch tasks based on their current status (e.g., PENDING, COMPLETED).  
- **Custom Exceptions:** Meaningful error messages for better debugging and client feedback.  
- **Static Token Security:** Basic API protection using a static token filter.

---

## Tech Stack

- **Backend Framework:** Spring Boot  
- **Database:** H2 (file-based storage)  
- **ORM:** Hibernate / JPA  
- **Build Tool:** Maven  
- **Language:** Java 21  

---

## Authentication

This project uses a **static token–based authentication filter**.  
To access any endpoint, clients must include a valid token in the HTTP header:

```
Authorization: <STATIC_TOKEN>
```

The token is configured in `application.yml`

---

## API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| `GET` | `/tasks?page=&size= ` | Fetch all tasks |
| `GET` | `/api/tasks/{id}` | Get a specific task by ID |
| `GET` | `/api/tasks?status=` | Filter tasks by status |
| `POST` | `/api/tasks` | Create a new task |
| `PUT` | `/api/tasks/{id}` | Update an existing task |
| `DELETE` | `/api/tasks/{id}` | Delete a task by ID |

---

