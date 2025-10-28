// ===================
// app.js - Frontend Logic and AJAX Calls
// ===================

// Dark mode toggle
const themeToggleBtn = document.getElementById('themeToggle');
if(themeToggleBtn){
  themeToggleBtn.addEventListener('click', () => {
    document.body.classList.toggle('dark');
    // Save theme preference locally if needed
    localStorage.setItem('theme', document.body.classList.contains('dark') ? 'dark' : 'light');
  });
}

// Restore theme from localStorage
window.addEventListener('load', () => {
  const savedTheme = localStorage.getItem('theme');
  if(savedTheme === 'dark'){
    document.body.classList.add('dark');
  }
});

// ===================
// STUDENT LOGIN FORM (example)
// ===================

const studentLoginForm = document.getElementById('studentLoginForm');
if(studentLoginForm){
  studentLoginForm.addEventListener('submit', e => {
    e.preventDefault();
    const formData = new FormData(studentLoginForm);
    fetch('../backend/student_login.php', {
      method: 'POST',
      body: formData
    })
    .then(res => res.json())
    .then(data => {
      if(data.success){
        window.location.href = 'dashboard.html'; 
      } else {
        alert('Login Failed: Invalid email or password.');
      }
    })
    .catch(err => {
      alert('Error: ' + err.message);
    });
  });
}

// ===================
// FETCH ATTENDANCE DATA (example for dashboard)
// ===================

function fetchAttendance(studentId) {
  fetch(`../backend/get_attendance.php?student_id=${studentId}`)
  .then(res => res.json())
  .then(data => {
    console.log("Attendance Data:", data);
    // Call your rendering functions here (render attendance %, charts, etc.)
  })
  .catch(err => console.error("Error fetching attendance:", err));
}

// ===================
// EXPORT REPORT DEMO
// ===================

function exportPDF() {
  alert('Demo: Export to PDF functionality coming soon!');
  // For real: Use jsPDF or backend PDF generation
}

function exportExcel() {
  alert('Demo: Export to Excel functionality coming soon!');
  // For real: Generate XLSX client or server side
}

// ===================
// PROFILE IMAGE PREVIEW
// ===================

const profilePicInput = document.getElementById('picUpload');
if(profilePicInput){
  profilePicInput.addEventListener('change', e => {
    if(e.target.files.length > 0){
      const img = document.getElementById('profilePic');
      img.src = URL.createObjectURL(e.target.files[0]);
    }
  });
}

// Faculty-Student Dashboard Connection System
// This file handles the connection between faculty and student dashboards

// Schedule sharing functionality
window.FacultyStudentConnection = {
  
  // Save faculty schedule and share with students
  saveFacultySchedule: function(timetableData, facultyName) {
    const globalTimetable = {
      lastUpdated: new Date().toISOString(),
      facultyName: facultyName || 'Faculty',
      schedules: timetableData
    };
    localStorage.setItem('globalFacultyTimetable', JSON.stringify(globalTimetable));
    console.log('Faculty schedule saved and shared with students');
    return true;
  },

  // Get faculty schedule for students
  getFacultySchedule: function() {
    const saved = localStorage.getItem('globalFacultyTimetable');
    return saved ? JSON.parse(saved) : null;
  },

  // Convert faculty timetable to student schedule format
  convertToStudentSchedule: function(facultyTimetable) {
    if (!facultyTimetable || !facultyTimetable.schedules) return [];
    
    const schedule = [];
    const days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    
    for (const [key, classData] of Object.entries(facultyTimetable.schedules)) {
      const [day, slot] = key.split('-');
      const dayName = days[['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'].indexOf(day)];
      
      if (dayName && classData) {
        schedule.push({
          day: dayName,
          time: classData.time || 'Time TBD',
          subject: classData.subject || 'Subject',
          teacher: classData.teacher || 'Teacher',
          room: classData.room || 'Room',
          slot: slot
        });
      }
    }
    
    return schedule;
  },

  // Display schedule in student dashboard
  displayScheduleInStudent: function() {
    const facultyTimetable = this.getFacultySchedule();
    if (!facultyTimetable) {
      console.log('No faculty schedule found');
      return false;
    }

    const schedule = this.convertToStudentSchedule(facultyTimetable);
    
    // Update student schedule display
    const scheduleContainer = document.getElementById('schedule-grid-body');
    if (scheduleContainer) {
      scheduleContainer.innerHTML = '';
      
      const days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
      
      days.forEach(day => {
        const daySchedule = schedule.filter(item => item.day === day);
        const daySlot = document.createElement('div');
        daySlot.className = 'schedule-slot';
        
        if (daySchedule.length > 0) {
          daySlot.innerHTML = daySchedule.map(item => `
            <div class="schedule-class">
              <strong>${item.subject}</strong><br>
              <small>${item.time}</small><br>
              <small>Room: ${item.room}</small>
            </div>
          `).join('<br>');
        } else {
          daySlot.innerHTML = '<small class="text-muted">No classes</small>';
        }
        
        scheduleContainer.appendChild(daySlot);
      });
      
      // Show sync info
      const syncInfo = document.querySelector('.schedule-sync-info');
      if (syncInfo) {
        syncInfo.innerHTML = `
          <small class="text-success">
            <i class="fas fa-sync"></i> Schedule synced from ${facultyTimetable.facultyName} 
            at ${new Date(facultyTimetable.lastUpdated).toLocaleString()}
          </small>
        `;
      }
      
      return true;
    }
    
    return false;
  },

  // Update today's schedule display
  updateTodaySchedule: function() {
    const facultyTimetable = this.getFacultySchedule();
    if (!facultyTimetable) return false;

    const schedule = this.convertToStudentSchedule(facultyTimetable);
    const today = new Date().toLocaleDateString('en-US', { weekday: 'long' });
    const todayClasses = schedule.filter(item => item.day === today);
    
    const todayContainer = document.getElementById('today-schedule');
    if (todayContainer) {
      if (todayClasses.length > 0) {
        todayContainer.innerHTML = todayClasses.map(cls => `
          <div class="today-class-item">
            <div class="class-time">${cls.time}</div>
            <div class="class-subject">${cls.subject}</div>
            <div class="class-room">Room: ${cls.room}</div>
          </div>
        `).join('');
      } else {
        todayContainer.innerHTML = `
          <div class="no-classes">
            <i class="fas fa-calendar-times text-muted"></i>
            <p>No classes scheduled for today</p>
          </div>
        `;
      }
      return true;
    }
    
    return false;
  }
};

// Auto-connect when page loads
document.addEventListener('DOMContentLoaded', function() {
  // Check if we're on student dashboard
  if (window.location.href.includes('student')) {
    // Auto-load faculty schedule
    setTimeout(() => {
      window.FacultyStudentConnection.displayScheduleInStudent();
      window.FacultyStudentConnection.updateTodaySchedule();
    }, 1000);
    
    // Update every 30 seconds
    setInterval(() => {
      window.FacultyStudentConnection.displayScheduleInStudent();
      window.FacultyStudentConnection.updateTodaySchedule();
    }, 30000);
  }
});

console.log('Faculty-Student Connection System Loaded');
