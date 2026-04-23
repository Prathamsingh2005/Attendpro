<?php
require_once 'db.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = sanitizeInput($_POST['username']);
    $password = $_POST['password'];
    
    if (empty($username) || empty($password)) {
        $error = "Please fill in all fields";
    } else {
        try {
            $stmt = $pdo->prepare("SELECT id, password, name FROM faculty WHERE username = ?");
            $stmt->execute([$username]);
            $user = $stmt->fetch();
            
            if ($user && verifyPassword($password, $user['password'])) {
                $_SESSION['user_id'] = $user['id'];
                $_SESSION['user_type'] = 'faculty';
                $_SESSION['user_name'] = $user['name'];
                
                echo json_encode(['success' => true, 'redirect' => '../faculty/dashboard.html']);
            } else {
                echo json_encode(['success' => false, 'message' => 'Invalid username or password']);
            }
        } catch (PDOException $e) {
            echo json_encode(['success' => false, 'message' => 'Database error']);
        }
    }
} else {
    echo json_encode(['success' => false, 'message' => 'Invalid request method']);
}
?>