<?php
require_once 'db.php';
checkLogin('faculty');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $subject_id = $_POST['subject_id'] ?? '';
    $date = $_POST['date'] ?? '';
    $attendance_data = $_POST['attendance'] ?? [];
    
    if (empty($subject_id) || empty($date) || empty($attendance_data)) {
        echo json_encode(['success' => false, 'message' => 'Missing required data']);
        exit;
    }
    
    try {
        $pdo->beginTransaction();
        
        // Delete existing attendance for this date and subject
        $stmt = $pdo->prepare("DELETE FROM attendance WHERE subject_id = ? AND date = ?");
        $stmt->execute([$subject_id, $date]);
        
        // Insert new attendance records
        $stmt = $pdo->prepare("
            INSERT INTO attendance (student_id, subject_id, date, status, marked_by) 
            VALUES (?, ?, ?, ?, ?)
        ");
        
        foreach ($attendance_data as $student_id => $status) {
            $stmt->execute([$student_id, $subject_id, $date, $status, $_SESSION['user_id']]);
        }
        
        $pdo->commit();
        echo json_encode(['success' => true, 'message' => 'Attendance saved successfully']);
        
    } catch (PDOException $e) {
        $pdo->rollBack();
        echo json_encode(['success' => false, 'message' => 'Failed to save attendance']);
    }
} else {
    echo json_encode(['success' => false, 'message' => 'Invalid request method']);
}
?>