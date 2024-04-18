-- Inserting users (PASSWORD = John1)
INSERT INTO user (first_name, last_name, password, email, created_at, role, point_id, streak_id) 
VALUES 
('User', 'User', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user@example.com', '2024-04-16 15:00:00', 'USER', 1, 1),
('Admin', 'Admin', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'admin@example.com', '2024-04-16 15:00:00', 'ADMIN', 2, 2);

-- Inserting points
INSERT INTO point (point_id, current_points, total_earned_points) VALUES (1, 120, 500), (2, 150, 600);

-- Inserting streaks
INSERT INTO streak (streak_id, current_streak, current_streak_created_at, current_streak_updated_at, highest_streak, highest_streak_created_at, highest_streak_ended_at) 
VALUES 
(1, 10, '2024-04-16 15:00:00', '2024-04-16 15:00:03', 15, '2020-04-16 15:00:00', '2023-04-16 15:00:00'),
(2, 8, '2024-04-16 15:00:00', '2024-04-16 15:00:03', 20, '2020-04-16 15:00:00', '2023-04-16 15:00:00');

-- Inserting friends
INSERT INTO friend (user_id, friend_id, pending, created_at) VALUES (1, 2, FALSE, '2024-04-16 15:00:00');