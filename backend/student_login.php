<?php
require_once 'db.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $roll_number = sanitizeInput($_POST['roll_number']);
    $password = $_POST['password'];
    
    if (empty($roll_number) || empty($password)) {
        echo json_encode(['success' => false, 'message' => 'Please fill in all fields']);
    } else {
        try {
            $stmt = $pdo->prepare("SELECT id, password, name, roll_number FROM students WHERE roll_number = ?");
            $stmt->execute([$roll_number]);
            $user = $stmt->fetch();
            
            if ($user && verifyPassword($password, $user['password'])) {
                $_SESSION['user_id'] = $user['id'];
                $_SESSION['user_type'] = 'student';
                $_SESSION['user_name'] = $user['name'];
                $_SESSION['roll_number'] = $user['roll_number'];
                
                echo json_encode(['success' => true, 'redirect' => '../student/dashboard.html']);
            } else {
                echo json_encode(['success' => false, 'message' => 'Invalid roll number or password']);
            }
        } catch (PDOException $e) {
            echo json_encode(['success' => false, 'message' => 'Database error']);
        }
    }
} else {
    echo json_encode(['success' => false, 'message' => 'Invalid request method']);
}
?>