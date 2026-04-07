# 🏥 Swasthyasathi – Rural Healthcare Management System

A full-stack healthcare platform designed to improve **medical accessibility for rural communities**, featuring appointment booking, teleconsultation, emergency services, and an **AI-powered chatbot using Google Gemini API**.

---

## 🚀 Features

### 🩺 Core Functionalities

* **Patient & Doctor Management** – CRUD operations for managing patient and doctor records
* **Appointment Scheduling** – Book, view, and manage appointments
* **Teleconsultation** – Integration with Zoom / Google Meet for remote consultations
* **🚑 Ambulance Service Module**

  * Fetch available ambulances from database
  * Returns *“No Ambulance Available”* when fully occupied

---

### 🤖 AI Chatbot (NEW)

* Integrated **Google Gemini API**
* Assists users with:

  * Health-related queries
  * Basic guidance and FAQs
  * Appointment-related help

---

### 🌐 Frontend (NEW)

* Interactive UI built with HTML, CSS, JavaScript
* Enables users to interact easily with backend services
* Provides a simple and clean healthcare interface

---

## 🧩 Architecture

Frontend (HTML/CSS/JS)
↓
REST APIs (Spring Boot)
↓
Service Layer (Business Logic)
↓
Spring Data JPA / Hibernate
↓
MySQL Database

---

## 🛠️ Tech Stack

| Layer           | Technologies               |
| --------------- | -------------------------- |
| Backend         | Spring Boot                |
| ORM             | Hibernate, Spring Data JPA |
| Database        | MySQL                      |
| Frontend        | HTML, CSS, JavaScript      |
| AI Integration  | Google Gemini API          |
| Build Tool      | Maven                      |
| Version Control | Git, GitHub                |

---

## 📸 Screenshots

*(Add images in an `/images` folder and link them here)*

![Home](images/home.png)
![Chatbot](images/chatbot.png)

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/Nilmoni04/rural-health-care-springboot.git
cd rural-health-care-improved
```

---

### 2️⃣ Configure Environment Variables

Create a `.env` file:

```
DB_URL=jdbc:mysql://localhost:3306/rural_healthcare_db
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
GEMINI_API_KEY=your_api_key
```

---

### 3️⃣ Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

Access the app at:
➡️ http://localhost:8080

---

## 📚 API Overview

| Endpoint          | Method              | Description              |
| ----------------- | ------------------- | ------------------------ |
| /api/patients     | GET/POST/PUT/DELETE | Manage patients          |
| /api/doctors      | GET/POST/PUT/DELETE | Manage doctors           |
| /api/appointments | GET/POST/DELETE     | Manage appointments      |
| /api/ambulance    | GET                 | Get available ambulances |
| /api/chat         | POST                | AI chatbot interaction   |

---

## 📈 Key Achievements

* Built scalable REST APIs using Spring Boot
* Integrated AI chatbot for real-time healthcare assistance
* Designed a functional frontend for better user interaction
* Optimized backend queries for improved performance

---

## 🔮 Future Improvements

* 🔐 Add authentication and role-based access
* 📊 Analytics dashboard
* 🌐 Multi-language support

---

## ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub!
ar ⭐ the repository — it motivates contributors to keep improving Swasthyasathi!
