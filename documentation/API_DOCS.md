# 📡 API Documentation — Hospital Management System

**Base URL:** `http://localhost:8080/api`

**Authentication:** All protected APIs require:

Authorization: Bearer {your_jwt_token}


---

## 📋 Table of Contents

- [Auth APIs](#auth-apis)
- [Doctor APIs](#doctor-apis)
- [Patient APIs](#patient-apis)
- [Appointment APIs](#appointment-apis)

---

## 🔐 Auth APIs

### 1. Register User

POST /api/auth/register

**Access:** Public — No token required

**Request Body:**
```json
{
    "firstName": "Mahendra",
    "lastName": "Kumar",
    "email": "mahendra@gmail.com",
    "password": "mahendra123",
    "phone": "6388019116",
    "role": "PATIENT"
}
```

**Role Options:**

ADMIN
DOCTOR
PATIENT
RECEPTIONIST


**Success Response (200):**
```json
{
    "id": 1,
    "firstName": "Mahendra",
    "lastName": "Kumar",
    "email": "mahendra@gmail.com",
    "password": null,
    "phone": "6388019116",
    "role": "PATIENT",
    "isActive": true,
    "createdAt": "2026-08-15T10:00:00",
    "updatedAt": "2026-08-15T10:00:00"
}
```

**Error Response (400):**
```json
Email already registered!
```

---

### 2. Login

POST /api/auth/login

**Access:** Public — No token required

**Request Body:**
```json
{
    "email": "admin@hms.com",
    "password": "admin123"
}
```

**Success Response (200):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "role": "ADMIN",
    "email": "admin@hms.com",
    "firstName": "Admin",
    "lastName": "HMS",
    "id": 1
}
```

**Error Response (400):**
```json
Invalid password!
```

⚠️ **Important:** Save this token — 
use it in all protected API calls!

---

### 3. Test API

GET /api/auth/test

**Access:** Public — No token required

**Success Response (200):**

HMS Backend is Running!


---

## 👨‍⚕️ Doctor APIs

### 1. Register Doctor

POST /api/admin/doctors/register

**Access:** ADMIN only

**Headers:**

Authorization: Bearer {admin_token}


**Request Body:**
```json
{
    "firstName": "Rahul",
    "lastName": "Sharma",
    "email": "rahul.doctor@hms.com",
    "password": "doctor123",
    "phone": "8888888888",
    "specialization": "Cardiology",
    "qualification": "MBBS, MD",
    "experienceYears": 5,
    "consultationFee": 500.0,
    "department": "Heart"
}
```

**Success Response (200):**
```json
{
    "id": 1,
    "user": {
        "firstName": "Rahul",
        "lastName": "Sharma",
        "email": "rahul.doctor@hms.com",
        "role": "DOCTOR"
    },
    "specialization": "Cardiology",
    "qualification": "MBBS, MD",
    "experienceYears": 5,
    "consultationFee": 500.0,
    "department": "Heart",
    "isAvailable": true
}
```

---

### 2. Get All Doctors

GET /api/admin/doctors/all

**Access:** ADMIN only

**Headers:**

Authorization: Bearer {admin_token}


**Success Response (200):**
```json
[
    {
        "id": 1,
        "specialization": "Cardiology",
        "user": {
            "firstName": "Rahul",
            "role": "DOCTOR"
        }
    }
]
```

---

### 3. Get Doctor By ID

GET /api/admin/doctors/{id}

**Access:** ADMIN only

**Example:**

GET /api/admin/doctors/1


**Success Response (200):**
```json
{
    "id": 1,
    "specialization": "Cardiology",
    "qualification": "MBBS, MD",
    "experienceYears": 5,
    "consultationFee": 500.0
}
```

**Error Response (404):**

Doctor not found!


---

### 4. Get Available Doctors

GET /api/admin/doctors/available

**Access:** ADMIN, PATIENT

**Success Response (200):**
```json
[
    {
        "id": 1,
        "isAvailable": true,
        "specialization": "Cardiology"
    }
]
```

---

### 5. Get Doctors By Specialization

GET /api/admin/doctors/specialization/{spec}

**Access:** ADMIN only

**Example:**

GET /api/admin/doctors/specialization/Cardiology


---

### 6. Update Doctor

PUT /api/admin/doctors/update/{id}

**Access:** ADMIN only

**Request Body:**
```json
{
    "specialization": "Neurology",
    "qualification": "MBBS, MD, DM",
    "experienceYears": 6,
    "consultationFee": 600.0,
    "department": "Brain"
}
```

---

### 7. Toggle Doctor Availability

PUT /api/admin/doctors/toggle/{id}

**Access:** ADMIN only

**Example:**

PUT /api/admin/doctors/toggle/1


**Success Response (200):**
```json
{
    "id": 1,
    "isAvailable": false
}
```

---

### 8. Delete Doctor

DELETE /api/admin/doctors/delete/{id}

**Access:** ADMIN only

**Success Response (200):**

Doctor deleted successfully!


---

## 🧑‍🤝‍🧑 Patient APIs

### 1. Register Patient

POST /api/admin/patients/register

**Access:** ADMIN only

**Headers:**

Authorization: Bearer {admin_token}


**Request Body:**
```json
{
    "firstName": "Rahul",
    "lastName": "Gupta",
    "email": "rahul.patient@hms.com",
    "password": "patient123",
    "phone": "5555555555",
    "dateOfBirth": "1995-06-15",
    "gender": "Male",
    "bloodGroup": "B+",
    "address": "Delhi, India",
    "emergencyContact": "9876543210",
    "medicalHistory": "Diabetes",
    "allergies": "Penicillin"
}
```

**Success Response (200):**
```json
{
    "id": 1,
    "dateOfBirth": "1995-06-15",
    "gender": "Male",
    "bloodGroup": "B+",
    "address": "Delhi, India",
    "user": {
        "firstName": "Rahul",
        "role": "PATIENT"
    }
}
```

---

### 2. Get All Patients

GET /api/admin/patients/all

**Access:** ADMIN, PATIENT

---

### 3. Get Patient By ID

GET /api/admin/patients/{id}

**Access:** ADMIN only

---

### 4. Get Patients By Blood Group

GET /api/admin/patients/bloodgroup/{bloodGroup}

**Access:** ADMIN only

**Example:**

GET /api/admin/patients/bloodgroup/B+


---

### 5. Update Patient

PUT /api/admin/patients/update/{id}

**Access:** ADMIN only

**Request Body:**
```json
{
    "gender": "Male",
    "bloodGroup": "O+",
    "address": "Mumbai, India",
    "emergencyContact": "9876543210",
    "medicalHistory": "Diabetes, BP",
    "allergies": "None"
}
```

---

### 6. Delete Patient

DELETE /api/admin/patients/delete/{id}

**Access:** ADMIN only

**Success Response (200):**

Patient deleted successfully!


---

## 📅 Appointment APIs

### 1. Book Appointment

POST /api/appointments/book

**Access:** ADMIN, PATIENT

**Headers:**

Authorization: Bearer {token}


**Request Body:**
```json
{
    "patientId": 1,
    "doctorId": 1,
    "appointmentDate": "2026-08-20",
    "appointmentTime": "10:30",
    "reason": "Chest pain"
}
```

**Success Response (200):**
```json
{
    "id": 1,
    "patient": {
        "id": 1,
        "gender": "Male"
    },
    "doctor": {
        "id": 1,
        "specialization": "Cardiology"
    },
    "appointmentDate": "2026-08-20",
    "appointmentTime": "10:30:00",
    "status": "PENDING",
    "reason": "Chest pain"
}
```

**Error Response (400):**

Doctor is not available!


---

### 2. Get All Appointments

GET /api/appointments/all

**Access:** ADMIN, DOCTOR, PATIENT

---

### 3. Get Appointment By ID

GET /api/appointments/{id}

**Access:** ADMIN, DOCTOR, PATIENT

---

### 4. Get Appointments By Patient

GET /api/appointments/patient/{patientId}

**Access:** ADMIN, DOCTOR, PATIENT

**Example:**

GET /api/appointments/patient/1


---

### 5. Get Appointments By Doctor

GET /api/appointments/doctor/{doctorId}

**Access:** ADMIN, DOCTOR, PATIENT

**Example:**

GET /api/appointments/doctor/1


---

### 6. Get Appointments By Status

GET /api/appointments/status/{status}

**Access:** ADMIN, DOCTOR, PATIENT

**Status Options:**

PENDING
CONFIRMED
COMPLETED
CANCELLED


**Example:**

GET /api/appointments/status/PENDING


---

### 7. Get Appointments By Date

GET /api/appointments/date/{date}

**Access:** ADMIN, DOCTOR, PATIENT

**Example:**

GET /api/appointments/date/2026-08-20


---

### 8. Get Doctor Schedule

GET /api/appointments/schedule/{doctorId}/{date}

**Access:** ADMIN, DOCTOR, PATIENT

**Example:**

GET /api/appointments/schedule/1/2026-08-20


---

### 9. Update Appointment Status

PUT /api/appointments/status/{id}

**Access:** ADMIN, DOCTOR

**Request Body:**
```json
{
    "status": "CONFIRMED"
}
```

**Success Response (200):**
```json
{
    "id": 1,
    "status": "CONFIRMED"
}
```

---

### 10. Add Doctor Notes

PUT /api/appointments/notes/{id}

**Access:** ADMIN, DOCTOR

**Request Body:**
```json
{
    "notes": "Patient has high BP. 
              Prescribed medicine."
}
```

**Success Response (200):**
```json
{
    "id": 1,
    "notes": "Patient has high BP.",
    "status": "COMPLETED"
}
```

---

### 11. Cancel Appointment

PUT /api/appointments/cancel/{id}

**Access:** ADMIN, DOCTOR, PATIENT

**Success Response (200):**
```json
{
    "id": 1,
    "status": "CANCELLED"
}
```

---

## 🔒 Authorization Summary

| API | ADMIN | DOCTOR | PATIENT |
|---|---|---|---|
| Register/Login | ✅ | ✅ | ✅ |
| Doctor APIs | ✅ | ❌ | ❌ |
| Patient APIs | ✅ | ❌ | ⚠️ |
| Book Appointment | ✅ | ❌ | ✅ |
| View Appointments | ✅ | ✅ | ✅ |
| Update Status | ✅ | ✅ | ❌ |
| Cancel | ✅ | ✅ | ✅ |

⚠️ = Limited access
