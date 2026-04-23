<?php
require_once 'db.php';

if ($_SESSION['user_type'] === 'faculty') {
    checkLogin('faculty');
    
    // Get students and subjects for faculty
    if (isset($_GET['action']) && $_GET['action'] === 'get_subjects') {
        try {
            $stmt = $pdo->prepare("SELECT id, subject_code, subject_name FROM subjects WHERE faculty_id = ?");
            $stmt->execute([$_SESSION['user_id']]);
            $subjects = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($subjects);
        } catch (PDOException $e) {
            echo json_encode(['error' => 'Database error']);
        }
    }
    
    elseif (isset($_GET['action']) && $_GET['action'] === 'get_students') {
        $subject_id = $_GET['subject_id'] ?? '';
        try {
            $stmt = $pdo->prepare("
                SELECT s.id, s.roll_number, s.name, s.department, s.semester 
                FROM students s 
                INNER JOIN subjects sub ON s.department = sub.department AND s.semester = sub.semester
                WHERE sub.id = ?
            ");
            $stmt->execute([$subject_id]);
            $students = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($students);
        } catch (PDOException $e) {
            echo json_encode(['error' => 'Database error']);
        }
    }
    
    elseif (isset($_GET['action']) && $_GET['action'] === 'get_attendance_report') {
        $subject_id = $_GET['subject_id'] ?? '';
        try {
            $stmt = $pdo->prepare("
                SELECT s.roll_number, s.name,
                       COUNT(a.id) as total_classes,
                       SUM(CASE WHEN a.status = 'present' THEN 1 ELSE 0 END) as present_count,
                       ROUND((SUM(CASE WHEN a.status = 'present' THEN 1 ELSE 0 END) * 100.0) / COUNT(a.id), 2) as percentage
                FROM students s
                LEFT JOIN attendance a ON s.id = a.student_id AND a.subject_id = ?
                INNER JOIN subjects sub ON s.department = sub.department AND s.semester = sub.semester
                WHERE sub.id = ?
                GROUP BY s.id, s.roll_number, s.name
                ORDER BY s.roll_number
            ");
            $stmt->execute([$subject_id, $subject_id]);
            $report = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($report);
        } catch (PDOException $e) {
            echo json_encode(['error' => 'Database error']);
        }
    }
    
} elseif ($_SESSION['user_type'] === 'student') {
    checkLogin('student');
    
    if (isset($_GET['action']) && $_GET['action'] === 'get_student_attendance') {
        try {
            $stmt = $pdo->prepare("
                SELECT sub.subject_name, sub.subject_code,
                       COUNT(a.id) as total_classes,
                       SUM(CASE WHEN a.status = 'present' THEN 1 ELSE 0 END) as present_count,
                       ROUND((SUM(CASE WHEN a.status = 'present' THEN 1 ELSE 0 END) * 100.0) / COUNT(a.id), 2) as percentage
                FROM subjects sub
                LEFT JOIN attendance a ON sub.id = a.subject_id AND a.student_id = ?
                INNER JOIN students s ON s.department = sub.department AND s.semester = sub.semester
                WHERE s.id = ?
                GROUP BY sub.id, sub.subject_name, sub.subject_code
                ORDER BY sub.subject_code
            ");
            $stmt->execute([$_SESSION['user_id'], $_SESSION['user_id']]);
            $attendance = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($attendance);
        } catch (PDOException $e) {
            echo json_encode(['error' => 'Database error']);
        }
    }
}
?>
