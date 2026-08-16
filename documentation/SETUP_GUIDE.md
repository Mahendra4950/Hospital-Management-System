# 🛠️ Setup Guide — Hospital Management System

This guide will help you run the HMS project 
on your local machine from scratch.

---

## ✅ Prerequisites

Make sure you have these installed:

| Tool | Version | Download |
|---|---|---|
| Java JDK | 17 or above | https://adoptium.net |
| Maven | 3.x | https://maven.apache.org |
| Node.js | 18 or above | https://nodejs.org |
| MySQL | 8.0 | https://dev.mysql.com |
| Git | Latest | https://git-scm.com |

**Verify installations:**
```bash
java --version
mvn --version
node --version
npm --version
mysql --version
git --version
```

---

## 📥 Step 1 — Clone The Project

```bash
git clone https://github.com/Mahendra4950/Hospital-Management-System.git

cd Hospital-Management-System
```

---

## 🗄️ Step 2 — Database Setup

**Open MySQL Workbench or MySQL CLI:**

```sql
-- Database create karo
CREATE DATABASE hospital_management_db;

-- Verify karo
SHOW DATABASES;
```

---

## ⚙️ Step 3 — Backend Configuration

**File:** `backend/src/main/resources/application.properties`

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_management_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Port
server.port=8080
```

⚠️ **Important:** Replace `YOUR_MYSQL_PASSWORD` 
with your actual MySQL password!

---

## 🚀 Step 4 — Run Backend

```bash
# Backend folder mein jao
cd backend

# Maven se run karo
mvn spring-boot:run
```

**Success message:**

Started BackendApplication in X seconds
Tomcat started on port 8080

**Verify:** Open browser and go to:

http://localhost:8080/api/auth/test

Response:

HMS Backend is Running!

---

## ⚛️ Step 5 — Run Frontend

**New terminal kholo:**

```bash
# Frontend folder mein jao
cd frontend

# Dependencies install karo
npm install

# Start karo
npm start
```

**Success message:**

Compiled successfully!
Local: http://localhost:3000


Browser automatically open hoga at:

http://localhost:3000
---

## 👤 Step 6 — Create First Admin User

**Using Postman or any API tool:**

POST → http://localhost:8080/api/auth/register
Content-Type: application/json


```json
{
    "firstName": "Admin",
    "lastName": "HMS",
    "email": "admin@hms.com",
    "password": "admin123",
    "role": "ADMIN",
    "phone": "9999999999"
}
```

---

## 🧪 Step 7 — Test Login

Open browser:

http://localhost:3000


Login with:

Email → admin@hms.com
Password → admin123


---

## 🔄 How It Works

User visits localhost:3000
↓
Login page dikhta hai
↓
Email + Password enter karo
↓
Backend JWT token generate karta hai
↓
Role ke hisaab se dashboard khulta hai:
ADMIN → Admin Dashboard
DOCTOR → Doctor Dashboard
PATIENT → Patient Dashboard


---

## ❌ Common Errors & Fixes

### Error 1 — Database Connection Failed

Communications link failure

**Fix:**
- MySQL service start hai?
- Password sahi hai application.properties mein?

### Error 2 — Port 8080 Already In Use

Port 8080 was already in use

**Fix:**
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID {PID_NUMBER} /F
```

### Error 3 — CORS Error in Browser

blocked by CORS policy

**Fix:**
- Backend run ho raha hai port 8080 pe?
- Frontend run ho raha hai port 3000 pe?

### Error 4 — npm not found

npm is not recognized

**Fix:**
- Node.js install karo from nodejs.org
- Terminal restart karo

### Error 5 — 401 Unauthorized

401 Unauthorized

**Fix:**
- Dobara login karo — fresh token lo
- Token 24 hours mein expire hota hai

---

## 📞 Support

Agar koi problem aaye:
- GitHub Issues: github.com/Mahendra4950/Hospital-Management-System/issues
- Email: mahendraverma4950@gmail.com
