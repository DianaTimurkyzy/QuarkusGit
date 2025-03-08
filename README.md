# Repository Data Service

A Quarkus-based REST API application that retrieves non-fork GitHub repositories for a specified user, including branch details, using the GitHub API v3.

## 🚀 Overview

- **Purpose:** Provides an API endpoint to retrieve non-fork GitHub repositories for a user, including:
    - Repository name
    - Owner login
    - Branch details (name & last commit SHA)
- **Backing API:** GitHub API v3
- **Endpoint:** `GET /repos/{username}`
- **Response Type:** `Uni<List<RepositoryResponseDto>>` for asynchronous processing

## 📜 API Specification

### 🔹 List Repositories
Retrieves non-fork repositories for a user:

**Response Format:**
```json
[
  {
    "name": "quarkus",
    "ownerLogin": "some-random-github-user",
    "branches": [
      {
        "name": "main",
        "commit": {
          "sha": "abc1234567890..."
        }
      }
    ]
  }
]
```

### 🔹 Handling Non-Existent Users
Returns a **404 error**:

```json
{
  "status": 404,
  "message": "GitHub user not found"
}
```
## 🔧 Prerequisites

- **Java 17** or higher
- **Maven** for building and running the project
- **GitHub Personal Access Token** with `public_repo` scope (for API authentication)

## 📦 Setup Instructions

### Configure GitHub Token:
If you have GitHub request rate limit exceeded:
Create a token in https://github.com/settings/personal-access-tokens and edit `src/main/resources/application.properties`
```properties
github.token=YOUR_PERSONAL_ACCESS_TOKEN
```
*Ensure the token has `public_repo` scope for public repositories.*

### 🔧 Build the Project:
*Simply run it in Intellij Idea.*

## 🖥 API Usage
Hit the API endpoint via postman or browser using HTTP method GET for:
http://localhost:8080/repos/{username}
http://localhost:8080/repos/some-random-github-user


