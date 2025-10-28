# 🎓 AttendPro - Student Management System

A comprehensive student management system with real-time features, built with Java Spring Boot backend and modern HTML/CSS/JavaScript frontend.

## 🌟 Key Features

- **Real-time Attendance Tracking** - Live updates when faculty marks attendance
- **Academic Performance Dashboard** - Grades, subjects, and overall performance
- **Class Schedule Management** - Weekly timetable with room and instructor details
- **Notice Board** - Real-time notices from admin and faculty
- **Study Notes System** - Personal note-taking with CRUD operations
- **Faculty Notes Sharing** - Shared study materials from faculty
- **Pomodoro Timer** - Study session timer with gamification
- **Analytics Dashboard** - Study time distribution and progress tracking
- **Achievement System** - Points, levels, and achievement badges

## 🚀 Tech Stack

### Backend
- **Java 11**
- **Spring Boot 2.7.0**
- **Spring Security**
- **Spring Data JPA**
- **MySQL Database**
- **Maven**

### Frontend
- **HTML5**
- **CSS3** (Custom CSS with CSS Variables)
- **JavaScript (ES6+)**
- **Bootstrap 5**
- **Font Awesome Icons**
- **Chart.js**

## 📁 Project Structure

```
AttendPro/
├── admin/                    # Admin dashboard files
│   ├── dashboard.html
│   ├── login.html
│   └── setup.html
├── faculty/                  # Faculty dashboard files
│   ├── dashboard.html
│   └── login.html
├── student/                  # Student dashboard files
│   ├── dashboard.html
│   └── login.html
├── backend-java/             # Java Spring Boot backend
│   ├── src/main/java/com/attendpro/
│   │   ├── controller/       # REST Controllers
│   │   ├── service/         # Business Logic
│   │   ├── repository/      # Data Access Layer
│   │   ├── entity/          # JPA Entities
│   │   └── dto/             # Data Transfer Objects
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── assets/                   # Shared assets
│   ├── app.js
│   └── style.css
└── README.md
```

## 🔧 Installation & Setup

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- MySQL 8.0+
- Modern web browser

### Backend Setup
1. Clone the repository:
```bash
git clone https://github.com/Prathamsingh2005/AttendPro.git
cd AttendPro
```

2. Navigate to backend:
```bash
cd backend-java
```

3. Configure database in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/attendpro
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

4. Run the application:
```bash
mvn spring-boot:run
```

### Frontend Setup
1. Open `student/dashboard.html` in a web browser
2. The dashboard will automatically connect to the backend API
3. For demo purposes, student ID 1 is used by default

## 📱 Usage

### Student Dashboard
1. Open `student/dashboard.html`
2. View real-time attendance, grades, and schedule
3. Create and manage study notes
4. Access faculty-shared materials
5. Use Pomodoro timer for study sessions
6. Track achievements and progress

### Faculty Dashboard
1. Open `faculty/dashboard.html`
2. Mark student attendance
3. Share study materials
4. View attendance reports

### Admin Dashboard
1. Open `admin/dashboard.html`
2. Manage users, classes, and subjects
3. Post notices
4. Configure system settings

## 🔄 Real-time Features

- **Automatic Updates**: Data refreshes every 30 seconds
- **Live Notifications**: Real-time alerts for new content
- **Instant Sync**: Changes appear immediately across all users
- **Background Refresh**: Seamless data synchronization

## 📊 API Endpoints

### Student APIs
- `GET /api/student/attendance?studentId={id}` - Get attendance data
- `GET /api/student/notices?classId={id}` - Get notices
- `GET /api/student/profile?studentId={id}` - Get profile
- `GET /api/student/grades?studentId={id}` - Get grades
- `GET /api/student/schedule?studentId={id}` - Get schedule
- `GET /api/student/faculty-notes?studentId={id}` - Get faculty notes
- `GET /api/student/dashboard-stats?studentId={id}` - Get dashboard stats

### Faculty APIs
- `POST /api/faculty/login` - Faculty login
- `GET /api/faculty/subjects?facultyId={id}` - Get subjects
- `GET /api/faculty/students?subjectId={id}` - Get students
- `POST /api/faculty/attendance` - Save attendance
- `GET /api/faculty/attendance-report?subjectId={id}` - Get reports

### Admin APIs
- `POST /api/admin/login` - Admin login
- `GET /api/admin/dashboard-stats` - Get stats
- `GET /api/admin/users` - Get all users
- `POST /api/admin/users` - Add user
- `GET /api/admin/notices` - Get notices
- `POST /api/admin/notices` - Add notice

## 🎨 UI/UX Features

- **Modern Design**: Clean and professional interface
- **Responsive Layout**: Works on desktop, tablet, and mobile
- **Interactive Elements**: Smooth animations and transitions
- **Accessibility**: Screen reader friendly
- **Mobile-First**: Optimized for mobile devices

## 🔒 Security Features

- **Authentication**: Secure login system
- **Authorization**: Role-based access control
- **Data Validation**: Input sanitization and validation
- **CORS Configuration**: Cross-origin request handling
- **Password Encryption**: Secure password storage

## 📈 Performance Optimizations

- **Lazy Loading**: Load data on demand
- **Caching**: Client-side data caching
- **Compression**: Optimized file sizes
- **Database Indexing**: Optimized queries

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Pratham Singh** - *Initial work* - [Prathamsingh2005](https://github.com/Prathamsingh2005)
  - Email: prathamgaur2005@gmail.com
  - GitHub: https://github.com/Prathamsingh2005

## 🙏 Acknowledgments

- Bootstrap for UI components
- Font Awesome for icons
- Chart.js for data visualization
- Spring Boot community for excellent documentation

## 📞 Support

If you have any questions or need help, please:
- Open an issue on GitHub
- Contact: prathamgaur2005@gmail.com
- Documentation: [Project Wiki](https://github.com/Prathamsingh2005/AttendPro/wiki)

---

⭐ **Star this repository if you found it helpful!**