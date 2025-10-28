# AttendPro Java Backend

This is the Java Spring Boot backend for the AttendPro system that replaces the PHP backend.

## Features

- **Admin Management**: Admin login, dashboard, user management
- **Faculty Management**: Faculty login, attendance marking, reports
- **Student Management**: Student login, attendance viewing, notices
- **Class & Subject Management**: CRUD operations for classes and subjects
- **Attendance System**: Mark and track student attendance
- **Notice System**: Create and manage notices for different audiences
- **System Settings**: Configure system-wide settings

## Technology Stack

- **Java 11**
- **Spring Boot 2.7.0**
- **Spring Security**
- **Spring Data JPA**
- **MySQL Database**
- **Maven**

## API Endpoints

### Admin APIs
- `POST /api/admin/login` - Admin login
- `GET /api/admin/dashboard-stats` - Get dashboard statistics
- `GET /api/admin/users` - Get all users
- `POST /api/admin/users` - Add new user
- `PUT /api/admin/users/{id}` - Update user
- `DELETE /api/admin/users/{id}` - Delete user
- `GET /api/admin/classes` - Get all classes
- `POST /api/admin/classes` - Add new class
- `GET /api/admin/subjects` - Get all subjects
- `POST /api/admin/subjects` - Add new subject
- `GET /api/admin/notices` - Get all notices
- `POST /api/admin/notices` - Add new notice
- `GET /api/admin/system-settings` - Get system settings
- `PUT /api/admin/system-settings` - Update system settings

### Faculty APIs
- `POST /api/faculty/login` - Faculty login
- `GET /api/faculty/subjects?facultyId={id}` - Get subjects by faculty
- `GET /api/faculty/students?subjectId={id}` - Get students by subject
- `POST /api/faculty/attendance` - Save attendance
- `GET /api/faculty/attendance-report?subjectId={id}` - Get attendance report

### Student APIs
- `POST /api/student/login` - Student login
- `GET /api/student/attendance?studentId={id}` - Get student attendance
- `GET /api/student/notices?classId={id}` - Get notices for student

## Database Setup

1. MySQL database `attendpro` create करें
2. Database tables automatically create होंगे (JPA auto-ddl)
3. Default admin user create होगा:
   - Username: `admin`
   - Password: `admin123`

## Running the Application

1. **Prerequisites**:
   - Java 11 or higher
   - Maven 3.6+
   - MySQL 8.0+

2. **Database Configuration**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/attendpro
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. **Build and Run**:
   ```bash
   cd backend-java
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the API**:
   - Base URL: `http://localhost:8080/api`
   - Admin Login: `POST http://localhost:8080/api/admin/login`

## Configuration

Application configuration `src/main/resources/application.properties` में है:

- Database connection settings
- JPA/Hibernate configuration
- Security settings
- CORS configuration
- Logging levels

## Security

- Spring Security implementation
- Password encryption using BCrypt
- Role-based access control
- CORS enabled for frontend integration

## Migration from PHP Backend

यह Java backend PHP backend के सभी functionality को provide करता है:

1. **Database Schema**: Same tables और relationships
2. **API Endpoints**: Same endpoints और responses
3. **Authentication**: Same login flow
4. **Business Logic**: Same functionality

Frontend को कोई changes की जरूरत नहीं है, सिर्फ API base URL change करना होगा।

## Development

### Project Structure
```
src/main/java/com/attendpro/
├── AttendProApplication.java          # Main application class
├── config/
│   └── SecurityConfig.java            # Security configuration
├── controller/                        # REST controllers
│   ├── AdminController.java
│   ├── FacultyController.java
│   └── StudentController.java
├── dto/                              # Data Transfer Objects
│   ├── LoginRequest.java
│   └── LoginResponse.java
├── entity/                           # JPA entities
│   ├── Admin.java
│   ├── Faculty.java
│   ├── Student.java
│   ├── Class.java
│   ├── Subject.java
│   ├── Attendance.java
│   ├── Notice.java
│   ├── SystemSettings.java
│   └── enums/
├── repository/                       # Data repositories
│   ├── AdminRepository.java
│   ├── FacultyRepository.java
│   ├── StudentRepository.java
│   ├── ClassRepository.java
│   ├── SubjectRepository.java
│   ├── AttendanceRepository.java
│   ├── NoticeRepository.java
│   └── SystemSettingsRepository.java
└── service/                          # Business logic
    ├── AdminService.java
    ├── FacultyService.java
    └── StudentService.java
```

### Adding New Features

1. **Entity**: Create JPA entity in `entity/` package
2. **Repository**: Create repository interface in `repository/` package
3. **Service**: Create service class in `service/` package
4. **Controller**: Create REST controller in `controller/` package
5. **DTO**: Create DTOs if needed in `dto/` package

## Testing

```bash
mvn test
```

## Troubleshooting

1. **Database Connection Issues**: Check MySQL service और credentials
2. **Port Conflicts**: Change `server.port` in application.properties
3. **CORS Issues**: Check CORS configuration in SecurityConfig
4. **Authentication Issues**: Check password encoding और user roles

## Support

अगर कोई issues हों तो check करें:
1. Application logs
2. Database connectivity
3. API endpoint URLs
4. Request/response formats
