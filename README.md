
# Friend Finder - Full Stack Application (uncompleted)

Friend Finder is a full-stack social media application built with **Angular (frontend)** and **Spring Boot (backend)**.  
It allows users to share posts, connect with friends, like, comment, and manage their social interactions in real time.

---

## 📸 Screenshots

| Login Page | Signup Page | Home page | Contact Us Page |
|------------|-------------|-----------|-----------------|
| ![Login Page](/screenshots/Screenshot1.png) |  ![Signup(Darkmode)](/screenshots/Screenshot2.png) | ![Home](/screenshots/Screenshot3.png) | ![contactUs](/screenshots/Screenshot4.png). 

---

## Features

### User Management
- Sign up / Login with email confirmation
- Profile picture upload
- View other user profiles

### Friend System
- Send friend requests
- Accept or decline friend requests
- View list of incoming requests
- Suggest friends based on mutual connections or recent activity

### Posts
- Create post (text only / with image / with video)
- View timeline of posts (self + friends)
- Like / Unlike posts
- Add comments and replies on posts
- Live updates for likes and comments via WebSocket

### Real-time Notifications
- Friend request notifications
- Like and comment notifications

### Media Handling
- Upload and preview image or video in posts
- Handle media storage and retrieval via Spring Boot and local/remote file system

---

## Tech Stack

| Layer       | Technology             |
|-------------|------------------------|
| Frontend    | Angular + RxJS + Tailwind CSS |
| Backend     | Spring Boot + Spring Security + JPA (Hibernate) |
| Database    | OracleDB             |
| Messaging   | WebSocket (STOMP over SockJS) |
| Auth        | JWT                    |
| Container   | Docker + Docker Compose |
| File Upload | Multipart + Static Resources |

---

## Project Structure

### Backend (Spring Boot)
- `/api/auth` – Authentication endpoints
- `/api/users` – User info and search
- `/api/friends` – Friend request logic
- `/api/posts` – Post creation and timeline
- `/api/comments` – Comment and reply handling
- `/api/notifications` – Real-time notification management
- `/uploads/` – Media storage route

### Frontend (Angular)
- Components: login, signup, profile, timeline, post, comment, friend list, suggestions, notifications
- Services: AuthService, PostService, FriendService, NotificationService
- Guards: AuthGuard, RoleGuard
- State: BehaviorSubjects for user and post state

---

## How to Run with Docker

### Prerequisites

- Docker
- Docker Compose

### Steps

1. Clone the repository:

```bash
git clone https://github.com/AbdurahmanHussien/Friend-finder-project.git
cd friend-finder
````

2. Build and start all services:

```bash
docker-compose up --build
```

3. The app will be available at:

* Frontend: `http://localhost:4200`
* Backend: `http://localhost:9090`
* Database: `localhost:1523` (OracleDb)

---


## Future Improvements

* Search with autocomplete
* User tagging in comments
* Group chats
* Image compression before upload
* Pagination and infinite scroll

---

## License

This project is open-source and free to use under MIT license.

