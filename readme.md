# App for making notes with markdown

I treat this project as hello world for learning new technologies.

## Features
- Create, edit and delete notes
- Notes are written in markdown
- Sharing notes via link
- User authentication
- Responsive design for mobile and desktop
- Dark and light themes
<!--- Search notes by title and content-->

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

### Infrastructure
- **Database**: PostgreSQL (Docker)
- **Caching**: Redis (Planned)
- **Deployment**: Docker & Docker Compose

## Setup Instructions
### Prerequisites
- Java 21
- Maven
- Node.js 18+
- Docker & Docker Compose
- PostgreSQL
- Redis (Planned)
### Backend Setup
1. Navigate to the backend directory:
    ```bash
    cd backend
    ```
2. Configure the database connection in `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/notely
    spring.datasource.username=your_username
    spring.datasource.password=your_password
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
