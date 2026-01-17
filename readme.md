# App for making notes with markdown

I treat this project as hello world for learning new technologies.



## Features
- Create, edit and delete notes
- Notes are written in markdown
- Sharing notes via link
- User authentication
- Responsive design for mobile and desktop
- Dark and light themes

### Frontend
- **Framework**: Vue.js 3 (Composition API)
- **Styling**: Tailwind CSS 4.x
- **Build Tool**: Vite
- **State Management**: Pinia
- **Routing**: Vue Router
- **HTTP Client**: Axios


### Backend
- **Framework**: Spring Boot 4.0.1
- **Language**: Java 21
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security with Argon2
- **API Docs**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven
- **Caching**: Redis

### Infrastructure
- **Database**: PostgreSQL (Docker)
- **Caching**: Redis 
- **Deployment**: Docker & Docker Compose

## Setup Instructions
### Prerequisites
- Java 21
- Maven
- Node.js 18+
- Docker & Docker Compose
- PostgreSQL
- Redis 

## Docs
- API Documentation: Accessible at `http://localhost:8080/swagger-ui.html` after starting the backend server.


### Backend Setup
1. Navigate to the backend directory:
    ```bash
    cd backend
    ```
2. Configure database, redis connections and other env vars in `docker-compose.yaml`:
    ```properties
        DB_HOST
        DB_PORT
        DB_NAME
        DB_USERNAME
        DB_PASSWORD
        REDIS_HOST
        REDIS_PORT
        JWT_SECRET
        SPRING_PROFILES_ACTIVE
        
    ```
3. Build and run the backend:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
### Frontend Setup
1. Navigate to the frontend directory:
    ```bash
    cd frontend
    ```
2. Install dependencies:
    ```bash
    npm install
    ```
3. Start the development server:
    ```bash
    npm run dev
    ```
### Docker Setup
1. Ensure Docker and Docker Compose are installed.
2. Navigate to the project root directory.
3. Build and start the services:
    ```bash
    docker-compose up --build
    ```