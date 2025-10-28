# 📋 AttendPro - Data Template

This file contains all the data placeholders that need to be filled for the complete functionality of the AttendPro system.

## 👤 Student Data Template

### Sample Students
```json
{
  "students": [
    {
      "id": 1,
      "name": "John Doe",
      "rollNumber": "STU001",
      "email": "john.doe@example.com",
      "department": "Computer Science",
      "semester": "3rd",
      "phone": "+91-9876543210",
      "classId": 1,
      "password": "hashed_password_here"
    },
    {
      "id": 2,
      "name": "Jane Smith",
      "rollNumber": "STU002",
      "email": "jane.smith@example.com",
      "department": "Computer Science",
      "semester": "3rd",
      "phone": "+91-9876543211",
      "classId": 1,
      "password": "hashed_password_here"
    }
  ]
}
```

## 👨‍🏫 Faculty Data Template

### Sample Faculty
```json
{
  "faculty": [
    {
      "id": 1,
      "name": "Dr. Sarah Johnson",
      "email": "sarah.johnson@example.com",
      "department": "Computer Science",
      "phone": "+91-9876543212",
      "password": "hashed_password_here"
    },
    {
      "id": 2,
      "name": "Prof. Michael Brown",
      "email": "michael.brown@example.com",
      "department": "Mathematics",
      "phone": "+91-9876543213",
      "password": "hashed_password_here"
    }
  ]
}
```

## 👨‍💼 Admin Data Template

### Sample Admin
```json
{
  "admin": [
    {
      "id": 1,
      "name": "Admin User",
      "email": "admin@attendpro.com",
      "password": "hashed_password_here"
    }
  ]
}
```

## 🏫 Class Data Template

### Sample Classes
```json
{
  "classes": [
    {
      "id": 1,
      "name": "CS-3A",
      "department": "Computer Science",
      "semester": "3rd",
      "year": 2024
    },
    {
      "id": 2,
      "name": "CS-3B",
      "department": "Computer Science",
      "semester": "3rd",
      "year": 2024
    }
  ]
}
```

## 📚 Subject Data Template

### Sample Subjects
```json
{
  "subjects": [
    {
      "id": 1,
      "name": "Data Structures",
      "code": "CS301",
      "department": "Computer Science",
      "credits": 4,
      "facultyId": 1
    },
    {
      "id": 2,
      "name": "Mathematics",
      "code": "MATH301",
      "department": "Mathematics",
      "credits": 3,
      "facultyId": 2
    },
    {
      "id": 3,
      "name": "Physics",
      "code": "PHY301",
      "department": "Physics",
      "credits": 3,
      "facultyId": 3
    }
  ]
}
```

## 📅 Schedule Data Template

### Sample Schedule
```json
{
  "schedule": [
    {
      "id": 1,
      "studentId": 1,
      "day": "Monday",
      "startTime": "09:00",
      "endTime": "10:30",
      "subject": "Data Structures",
      "room": "Room 101",
      "instructor": "Dr. Sarah Johnson"
    },
    {
      "id": 2,
      "studentId": 1,
      "day": "Monday",
      "startTime": "11:00",
      "endTime": "12:30",
      "subject": "Mathematics",
      "room": "Room 102",
      "instructor": "Prof. Michael Brown"
    }
  ]
}
```

## 📊 Attendance Data Template

### Sample Attendance Records
```json
{
  "attendance": [
    {
      "id": 1,
      "studentId": 1,
      "subjectId": 1,
      "date": "2024-01-15",
      "status": "PRESENT",
      "markedBy": 1
    },
    {
      "id": 2,
      "studentId": 1,
      "subjectId": 2,
      "date": "2024-01-15",
      "status": "PRESENT",
      "markedBy": 2
    }
  ]
}
```

## 📝 Notice Data Template

### Sample Notices
```json
{
  "notices": [
    {
      "id": 1,
      "title": "Mid-term Examination Schedule",
      "content": "Mid-term examinations will be conducted from March 15-20. Please check your schedule and prepare accordingly.",
      "targetAudience": "STUDENTS",
      "targetClassId": 1,
      "priority": "HIGH",
      "status": "PUBLISHED",
      "createdBy": 1,
      "createdAt": "2024-01-15T10:30:00"
    },
    {
      "id": 2,
      "title": "Library Hours Extended",
      "content": "Library will remain open until 10 PM during exam period for better study facilities.",
      "targetAudience": "ALL",
      "targetClassId": null,
      "priority": "MEDIUM",
      "status": "PUBLISHED",
      "createdBy": 1,
      "createdAt": "2024-01-14T14:20:00"
    }
  ]
}
```

## 📚 Faculty Notes Data Template

### Sample Faculty Notes
```json
{
  "facultyNotes": [
    {
      "id": 1,
      "title": "Data Structures - Binary Trees",
      "content": "Complete notes on binary tree operations, traversals, and implementations.",
      "subject": "Data Structures",
      "facultyName": "Dr. Sarah Johnson",
      "fileUrl": "/files/binary-trees-notes.pdf",
      "createdAt": "2024-01-15T10:30:00"
    },
    {
      "id": 2,
      "title": "Mathematics - Calculus Integration",
      "content": "Important integration techniques and solved examples for exam preparation.",
      "subject": "Mathematics",
      "facultyName": "Prof. Michael Brown",
      "fileUrl": "/files/calculus-integration.pdf",
      "createdAt": "2024-01-14T14:20:00"
    }
  ]
}
```

## 🎓 Grades Data Template

### Sample Grades
```json
{
  "grades": [
    {
      "id": 1,
      "studentId": 1,
      "subjectId": 1,
      "marks": 85,
      "maxMarks": 100,
      "grade": "A",
      "semester": "3rd",
      "year": 2024
    },
    {
      "id": 2,
      "studentId": 1,
      "subjectId": 2,
      "marks": 92,
      "maxMarks": 100,
      "grade": "A+",
      "semester": "3rd",
      "year": 2024
    }
  ]
}
```

## ⚙️ System Settings Template

### Sample System Settings
```json
{
  "systemSettings": {
    "institutionName": "Your Institution Name",
    "institutionAddress": "Your Institution Address",
    "academicYear": "2024-25",
    "semester": "3rd",
    "attendanceThreshold": 75,
    "maxAbsentDays": 10,
    "notificationEnabled": true,
    "emailNotifications": true,
    "smsNotifications": false
  }
}
```

## 🔧 Database Configuration

### MySQL Database Setup
```sql
-- Create database
CREATE DATABASE attendpro;

-- Use database
USE attendpro;

-- Create tables (these will be auto-created by JPA)
-- But you can also create manually if needed
```

### Application Properties
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/attendpro
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
server.servlet.context-path=/

# CORS Configuration
cors.allowed.origins=http://localhost:3000,http://localhost:8080
cors.allowed.methods=GET,POST,PUT,DELETE,OPTIONS
cors.allowed.headers=*
```

## 📧 Contact Information

- **Developer**: Pratham Singh
- **Email**: prathamgaur2005@gmail.com
- **GitHub**: https://github.com/Prathamsingh2005
- **Project Repository**: https://github.com/Prathamsingh2005/AttendPro

## 📝 Instructions

1. **Replace all placeholder data** with your actual data
2. **Update email addresses** with real email addresses
3. **Configure database** with your MySQL credentials
4. **Update institution details** in system settings
5. **Add real faculty and student data**
6. **Configure file upload paths** for faculty notes
7. **Set up email service** for notifications
8. **Configure SMS service** if needed

## 🚀 Next Steps

1. Fill in all the template data
2. Set up the database
3. Configure the application properties
4. Test all functionalities
5. Deploy to production server

---

**Note**: This is a template file. Replace all placeholder values with your actual data before using in production.
