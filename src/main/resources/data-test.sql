
-- Inserting points
INSERT INTO point (point_id, current_points, total_earned_points) 
VALUES 
(1, 120, 500),
(2, 150, 600),
(3, 200, 700),
(4, 180, 550),
(5, 220, 800),
(6, 160, 530),
(7, 190, 620),
(8, 250, 900),
(9, 170, 480),
(10, 210, 750),
(11, 230, 850),
(12, 270, 1000),
(13, 240, 920),
(14, 260, 980),
(15, 280, 1050);


-- Inserting streaks
INSERT INTO streak (streak_id, current_streak, current_streak_created_at, current_streak_updated_at, highest_streak, highest_streak_created_at, highest_streak_ended_at) 
VALUES 
(1, 10, '2024-04-16 15:00:00', '2024-04-16 15:00:03', 15, '2020-04-16 15:00:00', '2023-04-16 15:00:00'),
(2, 8, '2024-04-17 10:30:00', '2024-04-17 10:30:03', 20, '2020-04-17 10:30:00', '2023-04-17 10:30:00'),
(3, 7, '2024-04-18 08:45:00', '2024-04-18 08:45:03', 8, '2024-04-18 08:45:00', '2025-04-18 08:45:00'),
(4, 5, '2024-04-19 12:15:00', '2024-04-19 12:15:03', 4, '2024-04-19 12:15:00', '2025-04-19 12:15:00'),
(5, 8, '2024-04-20 17:20:00', '2024-04-20 17:20:03', 9, '2024-04-20 17:20:00', '2025-04-20 17:20:00'),
(6, 12, '2024-04-21 09:00:00', '2024-04-21 09:00:03', 14, '2024-04-21 09:00:00', '2025-04-21 09:00:00'),
(7, 9, '2024-04-22 11:45:00', '2024-04-22 11:45:03', 10, '2024-04-22 11:45:00', '2025-04-22 11:45:00'),
(8, 6, '2024-04-23 14:30:00', '2024-04-23 14:30:03', 7, '2024-04-23 14:30:00', '2025-04-23 14:30:00'),
(9, 4, '2024-04-24 16:20:00', '2024-04-24 16:20:03', 5, '2024-04-24 16:20:00', '2025-04-24 16:20:00'),
(10, 11, '2024-04-25 19:00:00', '2024-04-25 19:00:03', 13, '2024-04-25 19:00:00', '2025-04-25 19:00:00'),
(11, 8, '2024-04-26 08:30:00', '2024-04-26 08:30:03', 9, '2024-04-26 08:30:00', '2025-04-26 08:30:00'),
(12, 5, '2024-04-27 10:15:00', '2024-04-27 10:15:03', 6, '2024-04-27 10:15:00', '2025-04-27 10:15:00'),
(13, 9, '2024-04-28 12:45:00', '2024-04-28 12:45:03', 11, '2024-04-28 12:45:00', '2025-04-28 12:45:00'),
(14, 7, '2024-04-29 15:00:00', '2024-04-29 15:00:03', 8, '2024-04-29 15:00:00', '2025-04-29 15:00:00'),
(15, 3, '2024-04-30 17:30:00', '2024-04-30 17:30:03', 4, '2024-04-30 17:30:00', '2025-04-30 17:30:00');

-- Inserting points
INSERT INTO point (point_id, current_points, total_earned_points) 
VALUES 
(1, 120, 500), 
(2, 150, 600),
(3, 200, 700),
(4, 180, 550),
(5, 220, 800),
(6, 160, 530),
(7, 190, 620),
(8, 250, 900),
(9, 170, 480),
(10, 210, 750),
(11, 230, 850),
(12, 270, 1000),
(13, 240, 920),
(14, 260, 980),
(15, 280, 1050);

-- Inserting users (PASSWORD = John1)
INSERT INTO user (user_id, first_name, last_name, password, email, created_at, role, point_id, streak_id) 
VALUES 
(1, 'User', 'User', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user@example.com', '2024-04-16 15:00:00', 'USER', 1, 1),
(2, 'Admin', 'Admin', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'admin@example.com', '2024-04-16 15:00:00', 'ADMIN', 2, 2),
(3, 'TestUser1', 'One', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'testuser1@example.com', '2024-04-16 15:00:00', 'USER', 3, 3),
(4, 'TestUser2', 'Two', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'testuser2@example.com', '2024-04-16 15:00:00', 'USER', 4, 4),
(5, 'TestUser3', 'Three', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'testuser3@example.com', '2024-04-16 15:00:00', 'USER', 5, 5),
(6, 'User4', 'Four', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user4@example.com', '2024-04-16 15:00:00', 'USER', 6, 6),
(7, 'User5', 'Five', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user5@example.com', '2024-04-16 15:00:00', 'USER', 7, 7),
(8, 'User6', 'Six', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user6@example.com', '2024-04-16 15:00:00', 'USER', 8, 8),
(9, 'User7', 'Seven', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user7@example.com', '2024-04-16 15:00:00', 'USER', 9, 9),
(10, 'User8', 'Eight', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user8@example.com', '2024-04-16 15:00:00', 'USER', 10, 10),
(11, 'User9', 'Nine', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user9@example.com', '2024-04-16 15:00:00', 'USER', 11, 11),
(12, 'User10', 'Ten', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user10@example.com', '2024-04-16 15:00:00', 'USER', 12, 12),
(13, 'User11', 'Eleven', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user11@example.com', '2024-04-16 15:00:00', 'USER', 13, 13),
(14, 'User12', 'Twelve', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user12@example.com', '2024-04-16 15:00:00', 'USER', 14, 14),
(15, 'User13', 'Thirteen', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', 'user13@example.com', '2024-04-16 15:00:00', 'USER', 15, 15);

-- Inserting friends
INSERT INTO friend (user_id, friend_id, pending, created_at) 
VALUES 
(1, 5, FALSE, '2024-04-16 15:00:00'),
(2, 6, TRUE, '2024-04-16 15:00:00'),
(3, 7, FALSE, '2024-04-16 15:00:00'),
(4, 8, TRUE, '2024-04-16 15:00:00'),
(5, 9, TRUE, '2024-04-16 15:00:00'),
(6, 10, FALSE, '2024-04-16 15:00:00'),
(7, 11, TRUE, '2024-04-16 15:00:00'),
(8, 12, FALSE, '2024-04-16 15:00:00'),
(9, 13, FALSE, '2024-04-16 15:00:00'),
(10, 14, FALSE, '2024-04-16 15:00:00'),
(11, 15, TRUE, '2024-04-16 15:00:00'),
(12, 1, FALSE, '2024-04-16 15:00:00'),
(13, 2, TRUE, '2024-04-16 15:00:00'),
(14, 3, TRUE, '2024-04-16 15:00:00'),
(15, 4, FALSE, '2024-04-16 15:00:00');

-- Inserting bank profiles
INSERT INTO bank_profile (bank_profile_id, ssn) VALUES
-- SSN(Social security number):
(1, 31125453913),
(2, 31125451740),
(3, 31125458990);

-- Inserting bank accounts
INSERT INTO account (bban, balance, bank_profile_id) VALUES
-- BBAN(Basic Bank Account Number):

-- Bank Profile 1:
(12073650567, 100, 1),
(12097256355, 500000, 1),
(12032202452, 13000, 1),
(12041281683, 19372, 1),

-- Bank profile 2:
(12086851618, 50000, 2),
(12061174077, 3956, 2),

-- Bank profile 3:
(12093388613, 1004, 3),
(12064516157, 2003, 3),
(12056860272, 109, 3);

-- Inserting Budgets
INSERT INTO budget (budget_id, budget_amount, budget_name, created_at, expense_amount, user_id) VALUES
(1, 10000, 'April 2024', '2024-04-26 09:56:18.172098', 5000, 1),
(2, 20000, 'March 2024', '2024-04-26 09:56:18.172098', 5000, 1);

-- Inserting Expenses
INSERT INTO expense (expense_id, amount, description, budget_id) VALUES
(1, 8000, 'Rent', 1),
(2, 1000, 'Cheese', 1),
(3, 1000, 'Milk', 1),
(4, 2000, 'FIFA Points', 2),
(5, 10000, 'Rent', 2),
(6, 8000, 'Girlfriend', 2);

-- Inserting Feedbacks
INSERT INTO feedback (feedback_id, email, message, created_at) VALUES
(1, 'user@exmaple.com', 'This is a very good website', '2024-04-30 11:13:42.833664'),
(2, 'admin@exmaple.com', 'This is a very bad website', '2024-04-30 11:13:42.833664'),
(3, 'user@exmaple.com', 'This is a very good website', '2024-04-30 11:13:42.833664');

-- Inserting Badges
INSERT INTO badge (badge_id, badge_name, criteria, image_id) VALUES
(1, 'Saving Champ', 100, null),
(2, 'Saving Master', 300, null),
(3, 'Saving Lord 69', 500, null);

-- Inserting BadgeUser
INSERT INTO badge_user (earned_at, user_id, badge_id) VALUES
('2024-05-01 11:13:42.833664', 1, 1),
('2024-05-01 11:13:42.833664', 1, 2);

-- Inserting items
INSERT INTO item (item_id, price, image_id, item_name) VALUES 
(1, 100, null, 'Item 1'),
(2, 200, null, 'Item 2'),
(3, 150, null, 'Item 3'),
(4, 300, null, 'Item 4'),
(5, 250, null, 'Item 5');

-- Inserting inventory
INSERT INTO inventory (bought_at, item_id, user_id) 
VALUES 
('2024-04-16 15:00:00', 1, 1),
('2024-04-17 10:30:00', 2, 2),
('2024-04-18 08:45:00', 3, 3),
('2024-04-19 12:15:00', 4, 4),
('2024-04-20 17:20:00', 5, 5);
