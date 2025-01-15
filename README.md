# Pokémon Game - Backend

Welcome to the backend of the Pokémon Game! This project provides a RESTful API for interacting with Pokémon data, implementing functionalities like fetching random Pokémon, verifying guesses, and offering options to make the game fun and engaging.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [Endpoints](#endpoints)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

This backend application integrates with the [PokéAPI](https://pokeapi.co/) to fetch Pokémon data and provide a simple game where users guess Pokémon names based on their silhouette. The backend generates random Pokémon, creates multiple-choice options (including wrong ones), and verifies guesses.

---

## Features

- Fetch a random Pokémon and its silhouette.
- Generate multiple-choice options with the correct Pokémon name and two wrong ones.
- Validate user guesses.
- Handle errors gracefully with detailed responses.
- Unit-tested with comprehensive coverage for service and integration layers.

---

## Technologies

- **Java 17**
- **Spring Boot 3.4.1**
- **Maven**
- **RESTful APIs**
- **JUnit 5**
- **Mockito**
- **PokéAPI**

---

## Setup

### Prerequisites

Ensure you have the following installed on your machine:

- Java 17+
- Maven 3.6+
- Git

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yuriamorim23/pokemon-backend.git
   ```

2. Navigate to the project directory:
   ```bash
   cd pokemon-backend
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. The application will be available at `http://localhost:8080`.

---

## Endpoints

### 1. Fetch Random Pokémon
- **Endpoint**: `/api/v1/pokemon/random`
- **Method**: `GET`
- **Description**: Returns a random Pokémon's silhouette and multiple-choice options.
- **Example Request**:
  ```bash
  curl -X GET http://localhost:8080/api/v1/pokemon/random
  ```
- **Example Response**:
  ```json
  {
    "id": 25,
    "silhouetteImageUrl": "https://pokeapi.co/sprites/pikachu.png",
    "options": ["pikachu", "bulbasaur", "charmander"]
  }
  ```

### 2. Verify Guess
- **Endpoint**: `/api/v1/pokemon/verify`
- **Method**: `POST`
- **Parameters**:
  - `id` (required): Pokémon ID.
  - `guess` (required): User's guessed Pokémon name.
- **Description**: Validates the guess and returns whether it's correct.
- **Example Correct Guess Request**:
  ```bash
  curl -X POST "http://localhost:8080/api/v1/pokemon/verify?id=25&guess=pikachu"
  ```
- **Example Correct Guess Response**:
  ```json
  {
    "trueName": "pikachu",
    "fullImageUrl": "https://pokeapi.co/sprites/pikachu.png",
    "correct": true
  }
  ```
- **Example Incorrect Guess Request**:
  ```bash
  curl -X POST "http://localhost:8080/api/v1/pokemon/verify?id=25&guess=bulbasaur"
  ```
- **Example Incorrect Guess Response**:
  ```json
  {
    "trueName": "pikachu",
    "fullImageUrl": "https://pokeapi.co/sprites/pikachu.png",
    "correct": false
  }
  ```

---

## Error Handling

- **400 Bad Request**: Invalid inputs (e.g., missing parameters, incorrect formats).
- **500 Internal Server Error**: Issues with the PokéAPI integration or other internal errors.

Error responses are structured as:
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid Pokémon ID",
  "path": "/api/v1/pokemon/verify"
}
```

---

## Testing

1. Run tests:
   ```bash
   mvn test
   ```

2. Tests include:
   - Unit tests for service and integration layers.
   - Mocked external API calls.

---

## Contributing

Contributions are welcome! If you'd like to enhance the project:

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your feature"
   ```
4. Push to your branch:
   ```bash
   git push origin feature/your-feature
   ```
5. Open a pull request.

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.
