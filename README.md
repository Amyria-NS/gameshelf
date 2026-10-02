# GameShelf

GameShelf is a personal game library and tracking application built as a full-stack learning and portfolio project.

The application allows users to maintain a collection of games, track their platform and completion status, organize games by genre, and associate a cover image with each game.

The project was built to strengthen my practical experience with **Java, Spring Boot, PostgreSQL, REST APIs, JavaScript, and web application architecture**, while also providing an opportunity to practice testing, validation, database design, and Git-based development.

## Features

* Create, view, update, and delete games
* Track game platform and status
* Assign multiple genres to a game
* Create, update, and remove genre associations
* Search games by title
* Filter games by platform and status
* Sort games by selected fields
* Upload and replace game cover images
* Delete and retrieve game images
* Request validation using Jakarta Bean Validation
* Consistent API error responses
* Validation of referenced genre IDs
* Strict handling of unknown JSON fields
* Unit testing of service-layer business logic

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Data JPA / Hibernate
* Spring Web
* Jakarta Bean Validation
* JUnit
* Mockito
* Maven

### Database

* PostgreSQL

### Frontend

* HTML
* CSS
* Vanilla JavaScript
* Fetch API

### Development Tools

* Eclipse
* pgAdmin
* Postman
* Git / GitHub

## Architecture

GameShelf uses a layered backend architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

The frontend is separated into UI and API responsibilities:

```text
HTML / UI
    ↓
app.js
    ↓
API modules
    ↓
REST API
```

The JavaScript API layer is responsible for HTTP requests and response handling, allowing the UI code to remain separate from the details of communicating with the backend.

### Backend packages

```text
gameshelf/
├── controller/
├── service/
├── repository/
├── specification/
├── model/
└── dto/
```

## Database Design

The primary `games` table stores the core information about each game.

Genres are stored separately because a game can have multiple genres and a genre can belong to multiple games.

```text
games
  │
  │
  └── game_genres ─── genres
```

The `game_genres` table acts as the many-to-many junction table.

Game images use a one-to-one relationship with games:

```text
games ─── game_images
```

Only the image path is stored in the database. The actual image file is stored on the server's filesystem.

The database uses PostgreSQL enums for `platform` and `status`.

## REST API

The API is organized around the game's resources.

| Method   | Endpoint          | Purpose                  |
| -------- | ----------------- | ------------------------ |
| `POST`   | `/api/games`      | Create a game            |
| `GET`    | `/api/games`      | Retrieve games           |
| `GET`    | `/api/games/{id}` | Retrieve a specific game |
| `PUT`    | `/api/games/{id}` | Update a game            |
| `DELETE` | `/api/games/{id}` | Delete a game            |

Game retrieval supports optional filtering and sorting parameters, including:

* `search`
* `status`
* `platform`
* `sortBy`
* `direction`

The API uses response DTOs rather than exposing JPA entities directly.

A normal game response contains the game's core information, while the individual-game response also includes its associated genres.

## Validation and Error Handling

Incoming requests are validated using Jakarta Bean Validation.

For example, game titles must be present and cannot exceed the database's 100-character limit.

The application also validates referenced resources such as genre IDs. If a request references a genre that does not exist, the request is rejected rather than creating an incomplete relationship.

API errors use a consistent response format:

```json
{
    "code": 400,
    "message": "Invalid genre ID: 47"
}
```

Global exception handling is implemented using `@RestControllerAdvice`.

Unknown JSON properties are also rejected rather than silently ignored.

## Image Handling

Game images are uploaded using multipart form data.

The server generates a UUID-based filename and stores the file on disk. The database stores the corresponding file path.

```text
Client
  │
  │ multipart upload
  ▼
Spring Boot
  │
  ├── image file → filesystem
  │
  └── image path → PostgreSQL
```

Uploading a new image for a game replaces the existing image.

This approach keeps binary image data out of the database while still allowing the application to associate an image with a specific game.

## Testing

The backend includes unit tests for the service layer using **JUnit and Mockito**.

Current tests cover areas such as:

* Retrieving existing and non-existent games
* Creating games
* Completion-date business rules
* Genre validation
* Updating games
* Preserving or replacing genre associations
* Clearing genre associations
* Deleting games
* Filtering and sorting behavior

Mockito is used to isolate the service layer from repositories and other dependencies.

Integration testing of the database and JPA specifications is a potential future addition.

## Design Decisions

### Response DTOs

JPA entities are not returned directly from the REST API.

Response DTOs provide a boundary between the persistence model and the API representation. This also prevents bidirectional JPA relationships from causing recursive JSON responses.

### Separate Request and Response Models

Game creation/update requests are represented separately from responses.

For example, `dateAdded` is assigned by the application when a game is created rather than being accepted from the client.

### Genre Associations

Genre IDs are supplied by the client and resolved to existing `Genre` entities by the service layer.

When updating a game:

* `null` genre IDs preserve the existing genres
* an empty collection removes all genres
* a collection containing IDs replaces the existing genres

### Specifications

Game filtering uses Spring Data JPA `Specification`s.

This keeps individual filtering conditions separate and allows optional filters to be combined without creating a large number of repository query methods.

### Image Storage

Image files are stored on the filesystem while their paths are stored in PostgreSQL.

This keeps the database focused on application data while avoiding storing potentially large binary files directly in database rows.

## Running Locally

### Prerequisites

* Java 21+
* Maven
* PostgreSQL
* Git

### Database

Create a PostgreSQL database/schema and the required tables and enum types using the SQL schema included with the project.

The application expects PostgreSQL to be available on the configured port.

### Configuration

Database credentials and other environment-specific values should be provided through environment variables or the local Spring configuration.

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/postgres
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

spring.jpa.properties.hibernate.default_schema=gameshelf

gameshelf.image-upload-dir=./uploads/images
```

### Run the Application

Clone the repository and start the Spring Boot application using Maven or an IDE such as Eclipse.

The REST API can then be accessed through the configured local server.

Postman can be used to test the API independently of the frontend.

## Project Structure

```text
GameShelf/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── gameshelf/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── specification/
│   │   │       ├── model/
│   │   │       └── dto/
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       │       ├── api/
│   │       │       └── app.js
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── gameshelf/
│               └── ...
│
├── uploads/
├── pom.xml
└── README.md
```

## Future Improvements

Potential future improvements include:

* Pagination for larger game collections
* Additional integration tests
* More comprehensive frontend functionality
* Improved image/file validation
* Authentication and user accounts
* Additional game metadata
* Deployment to a hosted environment
* Docker-based local development and deployment

## Project Goals

GameShelf is primarily a learning and portfolio project. The goal is not simply to produce a working CRUD application, but to gain practical experience making architectural decisions and understanding how the different parts of a full-stack application interact.

Areas of focus include:

* Designing relational database schemas
* Building REST APIs with Spring Boot
* Separating controllers, services, repositories, and DTOs
* Handling validation and application errors
* Working with JPA relationships
* Writing unit tests with JUnit and Mockito
* Building a frontend without a framework
* Managing files alongside database records
* Practicing Git and GitHub-based development
