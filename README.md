# 🏥 Swasthyasathi – Rural Women Healthcare Management System

**Tech Stack:** Spring Boot • Spring Data JPA • Hibernate • MySQL  

Swasthyasathi is a **healthcare management platform** built to improve **medical accessibility for rural women**. It streamlines healthcare operations such as appointment scheduling, teleconsultation, and emergency ambulance services through a robust and scalable backend system.

---

## 🚀 Features

### 🩺 Core Functionalities
- **Patient & Doctor Management:** Secure CRUD operations for patients and doctors with role-based access control (Admin, Doctor, Patient).  
- **Appointment Scheduling:** Book, view, and manage appointments seamlessly.  
- **Teleconsultation Support:** Integration with **Zoom/Google Meet** for remote doctor consultations.  
- **Ambulance Service Module:**  
  - Fetches available ambulance details (ID and contact number) from the MySQL database.  
  - Returns **“No Ambulance Available”** when all vehicles are occupied.  
- **Role-Based Access:** Distinct permissions and dashboards for Admins, Doctors, and Patients.  
- **Database Optimization:** Efficient schema design and indexed queries for faster data retrieval.  

---

## ⚙️ Planned Enhancements
- 💬 **AI Chatbot Integration:** Assist patients in booking appointments, answering FAQs, and providing healthcare tips.  
- 📊 **Analytics Dashboard:** Visualize healthcare trends and doctor activity reports.  
- 🌐 **Multi-language Support:** Extend accessibility to regional languages for rural deployment.

---

## 🧩 Architecture Overview

Frontend (Optional: React / Angular)
↓
RESTful APIs (Spring Boot)
↓
Service Layer (Business Logic)
↓
Data Access Layer (Spring Data JPA, Hibernate)
↓
MySQL Database

yaml
Copy code

---

## 🛠️ Tech Stack

| Layer | Technologies Used |
|-------|--------------------|
| **Backend Framework** | Spring Boot |
| **ORM** | Hibernate, Spring Data JPA |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **Security** | Spring Security (Role-based access) |
| **Version Control** | Git, GitHub |

---

## 🧪 Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/swasthyasathi.git
cd swasthyasathi
2️⃣ Configure Database
Update your application.properties (or application.yml) file:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/rural_health_care
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3️⃣ Build and Run
bash
Copy code
mvn clean install
mvn spring-boot:run
Access the application at:
➡️ http://localhost:8080

📈 Key Achievements
Optimized query performance and indexing improved data efficiency by 25%.

Successfully handled 100+ patient records and 50+ doctor profiles.

Automated emergency response module for real-world healthcare deployment.

📚 API Overview
Endpoint	Method	Description
/api/patients	GET / POST / PUT / DELETE	Manage patient records
/api/doctors	GET / POST / PUT / DELETE	Manage doctor profiles
/api/appointments	GET / POST / DELETE	Manage appointments
/api/ambulance	GET	Fetch available ambulances
/api/auth/login	POST	User authentication

💡 Future Vision
To make Swasthyasathi a comprehensive, AI-powered, and multilingual healthcare solution bridging the medical gap in rural India.


⭐ Show your support
If you found this project helpful, please star ⭐ the repository — it motivates contributors to keep improving Swasthyasathi!
