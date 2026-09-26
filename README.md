# 🏥 Hospital Management System - Backend

A backend REST API for a **Hospital Management System (HMS)** built using **Java, Spring Boot, Spring Data JPA, Spring Security, and MySQL**.

The system is designed to digitize hospital operations including patient management, doctors, employees, appointments, departments, services, diagnosis, tasks, inventory, and treatment-related workflows.

---

## 🚀 Features

### 👤 User & Role Management
- User registration and authentication
- Role-based access control
- Supported roles:
  - Admin
  - Doctor
  - Patient
  - Employee
- User profile management

### 🧑‍⚕️ Doctor Management
- Doctor management
- View doctor information
- Manage doctor appointments
- View patient history
- Add diagnosis and prescription
- Manage patient treatment progress

### 🧑‍🤝‍🧑 Patient Management
- Patient registration
- Patient profile
- Appointment booking
- View appointment history
- View diagnosis and prescriptions
- View medical/treatment information

### 📅 Appointment Management
- Create appointments
- View appointments
- Appointment details
- Appointment types
- Doctor-patient appointment relationship
- Diagnosis associated with appointments

### 🩺 Diagnosis Management
- Create diagnosis for an appointment
- Update diagnosis
- Store diagnosis description
- Store prescription information
- Retrieve diagnosis by appointment

### 🏢 Department Management
- Create departments
- Update departments
- Delete departments
- Assign services to departments
- Retrieve department services

### 🛠️ Hospital Services
- Create hospital services
- Update services
- Delete services
- Assign users/employees to services
- Manage service fees
- Manage service-related inventory

### 📦 Inventory Management
- Add inventory items
- Update inventory
- Update stock
- Assign inventory to patients
- Remove inventory from patients
- Delete inventory
- View inventory associated with services

### 📋 Task Management
- Create employee tasks
- Assign tasks to users/employees
- Update task status
- Track task progress
- Complete tasks

### 🔐 Security
- Authentication
- Authorization
- Role-based API access
- Protected endpoints using Spring Security

---

# 🛠️ Tech Stack

Java -> Backend Programming 
Spring Boot -> Backend Framework 
Spring Data JPA -> Database Operations 
Hibernate -> ORM 
Spring Security -> Authentication & Authorization 
MySQL -> Database 
Lombok -> Reduce Boilerplate Code 
REST API -> Client-Server Communication 
Jakarta Validation -> Request Validation 
