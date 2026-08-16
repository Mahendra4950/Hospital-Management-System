# 🏥 Hospital Management System (HMS)

A full-stack web application for managing hospital 
operations including doctors, patients, and appointments
with role-based access control.

---

## 📋 Table of Contents

- [About The Project](#about-the-project)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [User Roles](#user-roles)
- [API Overview](#api-overview)
- [Author](#author)

---

## 🎯 About The Project

Hospital Management System is a secure web application
that allows hospitals to manage their day-to-day 
operations digitally.

**Problems it solves:**
- Manual appointment booking is slow and error-prone
- Patient records are hard to manage on paper
- Doctors need a digital way to manage their schedule
- Admin needs a central dashboard for hospital overview

---

## ✨ Features

### 👨‍💼 Admin
- View all doctors, patients, appointments
- Register new doctors
- Register new patients
- Monitor hospital statistics

### 👨‍⚕️ Doctor
- View assigned appointments
- Confirm or cancel appointments
- Add medical notes after consultation
- Mark appointments as completed

### 🧑‍🤝‍🧑 Patient
- Register and login securely
- Book appointments with available doctors
- View appointment history
- Cancel pending appointments

---

## 🛠️ Tech Stack

### Backend
| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Main language |
| Spring Boot | 3.x | Backend framework |
| Spring Security | 3.x | Authentication |
| JWT | 0.11.5 | Token based auth |
| Hibernate | 6.x | ORM |
| MySQL | 8.0 | Database |
| Maven | 3.x | Build tool |

### Frontend
| Technology | Purpose |
|---|---|
| React.js | UI Framework |
| Bootstrap 5 | Styling |
| Axios | API calls |
| React Router | Navigation |

### Tools
| Tool | Purpose |
|---|---|
| IntelliJ IDEA | Backend IDE |
| VS Code | Frontend IDE |
| Postman | API Testing |
| MySQL Workbench | Database GUI |
| Git & GitHub | Version Control |

---

## 📁 Project Structure

Hospital-Management-System/
├── backend/
│ └── src/main/java/com/hms/backend/
│ ├── config/
│ │ ├── JwtFilter.java
│ │ ├── JwtUtil.java
│ │ └── SecurityConfig.java
│ ├── controller/
│ │ ├── AdminController.java
│ │ ├── AppointmentController.java
│ │ ├── AuthController.java
│ │ ├── DoctorController.java
│ │ └── PatientController.java
│ ├── entity/
│ │ ├── Appointment.java
│ │ ├── Doctor.java
│ │ ├── Patient.java
│ │ └── User.java
│ ├── enums/
│ │ └── Role.java
│ ├── repository/
│ │ ├── AppointmentRepository.java
│ │ ├── DoctorRepository.java
│ │ ├── PatientRepository.java
│ │ └── UserRepository.java
│ └── service/
│ ├── AppointmentService.java
│ ├── DoctorService.java
│ ├── PatientService.java
│ └── UserService.java
│
├── frontend/
│ └── src/
│ ├── pages/
│ │ ├── Login.js
│ │ ├── admin/AdminDashboard.js
│ │ ├── doctor/DoctorDashboard.js
│ │ └── patient/PatientDashboard.js
│ └── services/
│ ├── api.js
│ └── authService.js
│
└── documentation/
├── README.md
├── SETUP_GUIDE.md
├── API_DOCS.md
└── DATABASE.md

---

## 👥 User Roles

| Role | Access |
|---|---|
| **ADMIN** | Full access — doctors, patients, appointments |
| **DOCTOR** | Own appointments — confirm, complete, cancel |
| **PATIENT** | Book appointments, view own history |
| **RECEPTIONIST** | Future scope |

---

## 🔐 Security

- **JWT Token** based stateless authentication
- **BCrypt** password encryption
- **Role Based Access Control (RBAC)**
- Token expires in **24 hours**
- All protected APIs require `Authorization: Bearer {token}`

---

## 🚀 Getting Started

See [SETUP_GUIDE.md](./SETUP_GUIDE.md) for 
detailed installation instructions.

**Quick Start:**
```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend
cd frontend
npm install
npm start
```

---

## 📡 API Overview

See [API_DOCS.md](./API_DOCS.md) for 
complete API documentation.

**Base URL:** `http://localhost:8080/api`

| Module | Base Path |
|---|---|
| Auth | `/api/auth` |
| Admin | `/api/admin` |
| Appointments | `/api/appointments` |

---

## 👨‍💻 Author

**Mahendra Kumar**
- Java Full Stack Developer
- Email: mahendraverma4950@gmail.com
- GitHub: github.com/Mahendra4950

---

## 📄 License

This project is for educational and 
portfolio purposes.
