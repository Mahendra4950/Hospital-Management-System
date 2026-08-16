# 🗄️ Database Documentation — Hospital Management System

**Database Name:** `hospital_management_db`
**Database Type:** MySQL 8.0
**ORM:** Hibernate (Auto table generation)

---

## 📋 Table of Contents

- [Tables Overview](#tables-overview)
- [Table Details](#table-details)
- [Relationships](#relationships)
- [Sample Queries](#sample-queries)

---

## 📊 Tables Overview

| Table | Description | Records |
|---|---|---|
| `users` | All system users | Admin, Doctor, Patient |
| `doctors` | Doctor profiles | Linked to users |
| `patients` | Patient profiles | Linked to users |
| `appointments` | Appointments | Linked to doctor + patient |

---

## 📝 Table Details

### 1. users table

```sql
CREATE TABLE users (
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name    VARCHAR(50)  NOT NULL,
    last_name     VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    phone         VARCHAR(15)  UNIQUE,
    role          ENUM('ADMIN','DOCTOR',
                       'PATIENT','RECEPTIONIST') 
                  NOT NULL,
    is_active     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at    DATETIME     NOT NULL,
    updated_at    DATETIME     NOT NULL
);
```

**Field Details:**

| Field | Type | Description |
|---|---|---|
| `id` | BIGINT | Auto increment primary key |
| `first_name` | VARCHAR(50) | User ka first name |
| `last_name` | VARCHAR(50) | User ka last name |
| `email` | VARCHAR(100) | Unique email — login ke liye |
| `password` | VARCHAR(255) | BCrypt encrypted password |
| `phone` | VARCHAR(15) | Unique phone number |
| `role` | ENUM | ADMIN/DOCTOR/PATIENT/RECEPTIONIST |
| `is_active` | BOOLEAN | Account active hai ya nahi |
| `created_at` | DATETIME | Record creation time |
| `updated_at` | DATETIME | Last update time |

---

### 2. doctors table

```sql
CREATE TABLE doctors (
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id           BIGINT       NOT NULL UNIQUE,
    specialization    VARCHAR(100) NOT NULL,
    qualification     VARCHAR(100) NOT NULL,
    experience_years  INT          NOT NULL,
    consultation_fee  DOUBLE       NOT NULL,
    is_available      BOOLEAN      NOT NULL DEFAULT TRUE,
    phone             VARCHAR(15),
    department        VARCHAR(100),
    created_at        DATETIME     NOT NULL,
    updated_at        DATETIME     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

**Field Details:**

| Field | Type | Description |
|---|---|---|
| `id` | BIGINT | Auto increment primary key |
| `user_id` | BIGINT | Foreign key — users table |
| `specialization` | VARCHAR(100) | Cardiology, Neurology etc |
| `qualification` | VARCHAR(100) | MBBS, MD, MS etc |
| `experience_years` | INT | Years of experience |
| `consultation_fee` | DOUBLE | Fee per appointment |
| `is_available` | BOOLEAN | Available for appointments? |
| `phone` | VARCHAR(15) | Contact number |
| `department` | VARCHAR(100) | Heart, Brain, Bones etc |

---

### 3. patients table

```sql
CREATE TABLE patients (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id             BIGINT       NOT NULL UNIQUE,
    date_of_birth       DATE         NOT NULL,
    gender              VARCHAR(10)  NOT NULL,
    blood_group         VARCHAR(5),
    address             VARCHAR(255),
    emergency_contact   VARCHAR(15),
    medical_history     VARCHAR(500),
    allergies           VARCHAR(255),
    created_at          DATETIME     NOT NULL,
    updated_at          DATETIME     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

**Field Details:**

| Field | Type | Description |
|---|---|---|
| `id` | BIGINT | Auto increment primary key |
| `user_id` | BIGINT | Foreign key — users table |
| `date_of_birth` | DATE | Patient ki date of birth |
| `gender` | VARCHAR(10) | Male/Female/Other |
| `blood_group` | VARCHAR(5) | A+, B+, O+, AB+ etc |
| `address` | VARCHAR(255) | Patient ka address |
| `emergency_contact` | VARCHAR(15) | Emergency contact number |
| `medical_history` | VARCHAR(500) | Purani bimariyan |
| `allergies` | VARCHAR(255) | Kisi cheez se allergy |

---

### 4. appointments table

```sql
CREATE TABLE appointments (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id          BIGINT       NOT NULL,
    doctor_id           BIGINT       NOT NULL,
    appointment_date    DATE         NOT NULL,
    appointment_time    TIME         NOT NULL,
    status              VARCHAR(20)  NOT NULL 
                        DEFAULT 'PENDING',
    reason              VARCHAR(255),
    notes               VARCHAR(500),
    created_at          DATETIME     NOT NULL,
    updated_at          DATETIME     NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id)  REFERENCES doctors(id)
);
```

**Field Details:**

| Field | Type | Description |
|---|---|---|
| `id` | BIGINT | Auto increment primary key |
| `patient_id` | BIGINT | Foreign key — patients table |
| `doctor_id` | BIGINT | Foreign key — doctors table |
| `appointment_date` | DATE | Appointment ki date |
| `appointment_time` | TIME | Appointment ka time |
| `status` | VARCHAR(20) | PENDING/CONFIRMED/COMPLETED/CANCELLED |
| `reason` | VARCHAR(255) | Visit ka reason |
| `notes` | VARCHAR(500) | Doctor ke medical notes |

---

## 🔗 Relationships

users (1) ──────── (1) doctors
│
└────────────── (1) patients

doctors (1) ─────── (Many) appointments
patients (1) ────── (Many) appointments


**Explanation:**

| Relationship | Type | Meaning |
|---|---|---|
| users → doctors | One to One | Ek user sirf ek doctor ho sakta hai |
| users → patients | One to One | Ek user sirf ek patient ho sakta hai |
| doctors → appointments | One to Many | Ek doctor ke multiple appointments |
| patients → appointments | One to Many | Ek patient ke multiple appointments |

---

## 🔍 Sample Queries

### Sare Doctors Dekhna
```sql
SELECT u.first_name, u.last_name, 
       d.specialization, d.consultation_fee
FROM doctors d
JOIN users u ON d.user_id = u.id;
```

### Sare Patients Dekhna
```sql
SELECT u.first_name, u.last_name,
       p.blood_group, p.gender
FROM patients p
JOIN users u ON p.user_id = u.id;
```

### Pending Appointments Dekhna
```sql
SELECT a.appointment_date,
       a.appointment_time,
       a.status,
       u1.first_name AS patient_name,
       u2.first_name AS doctor_name
FROM appointments a
JOIN patients p ON a.patient_id = p.id
JOIN doctors d ON a.doctor_id = d.id
JOIN users u1 ON p.user_id = u1.id
JOIN users u2 ON d.user_id = u2.id
WHERE a.status = 'PENDING';
```

### Doctor Ka Schedule Dekhna
```sql
SELECT a.appointment_date,
       a.appointment_time,
       a.status,
       u.first_name AS patient_name
FROM appointments a
JOIN patients p ON a.patient_id = p.id
JOIN users u ON p.user_id = u.id
WHERE a.doctor_id = 1
ORDER BY a.appointment_date, 
         a.appointment_time;
```

### Blood Group Wise Patients
```sql
SELECT u.first_name, u.last_name,
       p.blood_group
FROM patients p
JOIN users u ON p.user_id = u.id
WHERE p.blood_group = 'B+'
ORDER BY u.first_name;
```

---

## 📈 Appointment Status Flow

PENDING
↓
CONFIRMED ←──── Doctor confirms
↓
COMPLETED ←──── Doctor adds notes

OR

PENDING/CONFIRMED
↓
CANCELLED ←──── Doctor or Patient cancels


---

## 🔐 Security Notes

- Passwords are **never stored in plain text**
- BCrypt algorithm used for hashing
- Each password hash is unique even for same password
- JWT tokens expire after **24 hours**

```sql
-- Password in database looks like this:
-- $2a$10$xK8Jz9mN2pL4qR6sT8uV.eWyA1bC3dE5fG7hI9j...
-- NEVER store plain text passwords!
```
