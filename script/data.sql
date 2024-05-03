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
(15, 3, '2024-04-30 17:30:00', '2024-04-30 17:30:03', 4, '2024-04-30 17:30:00', '2025-04-30 17:30:00'),
(16, 6, '2024-05-01 19:00:00', '2024-05-01 19:00:03', 7, '2024-05-01 19:00:00', '2025-05-01 19:00:00'),
(17, 8, '2024-05-02 08:00:00', '2024-05-02 08:00:03', 9, '2024-05-02 08:00:00', '2025-05-02 08:00:00'),
(18, 5, '2024-05-03 10:00:00', '2024-05-03 10:00:03', 6, '2024-05-03 10:00:00', '2025-05-03 10:00:00'),
(19, 7, '2024-05-04 12:00:00', '2024-05-04 12:00:03', 8, '2024-05-04 12:00:00', '2025-05-04 12:00:00'),
(20, 9, '2024-05-05 14:00:00', '2024-05-05 14:00:03', 10, '2024-05-05 14:00:00', '2025-05-05 14:00:00');

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
(15, 280, 1050),
(16, 300, 1200),
(17, 320, 1350),
(18, 340, 1500),
(19, 360, 1650),
(20, 380, 1800);

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

-- Inserting items
INSERT INTO item (item_id, price, image_id, item_name) VALUES 
(1, 0, 1, 'Stacked Banner'),
(2, 100, 2, 'Blob Banner'),
(3, 500, 3, 'Layered Banner'),
(4, 1000, 4, 'Stars Banner'),
(5, 5000, 5, 'Steps Banner'),
(6, 5000, 6, 'Waves Banner');

-- Inserting Badges
INSERT INTO badge (badge_id, badge_name, criteria, image_id) VALUES
(1, 'Awesome Badge', 100, 7),
(2, 'Super Cool Badge', 200, 8),
(3, 'Epic Badge', 300, 9),
(4, 'Radical Badge', 500, 10),
(5, 'Amazing Badge', 800, 11),
(6, 'Legendary Badge', 1200, 12),
(7, 'Mega Badge', 2000, 13),
(8, 'Ultra Badge', 3200, 14),
(9, 'Extreme Badge', 5200, 15),
(10, 'Ultimate Badge', 8400, 16);

INSERT INTO badge_
-- Inserting inventory
INSERT INTO inventory (bought_at, item_id, user_id) 
VALUES 
(NOW(), 1, 1),
(NOW(), 1, 2),
(NOW(), 1, 3),
(NOW(), 1, 4),
(NOW(), 1, 5),
(NOW(), 1, 6),
(NOW(), 1, 7),
(NOW(), 1, 8),
(NOW(), 1, 9),
(NOW(), 1, 10),
(NOW(), 1, 11),
(NOW(), 1, 12),
(NOW(), 1, 13),
(NOW(), 1, 14),
(NOW(), 1, 15),
(NOW(), 1, 16),
(NOW(), 1, 17),
(NOW(), 1, 18),
(NOW(), 1, 19),
(NOW(), 1, 20);

INSERT INTO notification (notification_id, created_at, message, notification_type, unread, user_id)
VALUES
(1, '2024-05-2 08:00:00', 'You have received a new friend request', 'FRIEND_REQUEST' ,true, 1),
(2, '2024-05-2 08:00:00', 'You have earned a new badge', 'BADGE', true, 1),
(3, '2024-05-2 08:00:00', 'You have completed a new goal', 'COMPLETED_GOAL', false, 1);

INSERT INTO configuration (configuration_id, commitment, experience) VALUES 
(1, 'LITTLE', 'NONE'),
(2, 'SOME', 'SOME'),
(3, 'MUCH', 'EXPERT'),
(4, 'LITTLE', 'EXPERT'),
(5, 'MUCH', 'SOME'),
(6, 'SOME', 'NONE'),
(7, 'MUCH', 'SOME'),
(8, 'LITTLE', 'SOME'),
(9, 'SOME', 'EXPERT'),
(10, 'LITTLE', 'NONE'),
(11, 'MUCH', 'SOME'),
(12, 'SOME', 'NONE'),
(13, 'LITTLE', 'EXPERT'),
(14, 'MUCH', 'EXPERT'),
(15, 'SOME', 'SOME'),
(16, 'MUCH', 'NONE'),
(17, 'LITTLE', 'SOME'),
(18, 'SOME', 'EXPERT'),
(19, 'MUCH', 'NONE'),
(20, 'LITTLE', 'SOME');

-- For configuration_id = 1
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(1, 'NO_COFFEE'),
(1, 'NO_CAR'),
(1, 'SHORTER_SHOWER');

-- For configuration_id = 2
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(2, 'SPEND_LESS_ON_FOOD'),
(2, 'BUY_USED_CLOTHES'),
(2, 'LESS_SHOPPING'),
(2, 'DROP_SUBSCRIPTION');

-- For configuration_id = 3
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(3, 'SELL_SOMETHING'),
(3, 'BUY_USED'),
(3, 'EAT_PACKED_LUNCH'),
(3, 'STOP_SHOPPING'),
(3, 'ZERO_SPENDING');

-- For configuration_id = 4
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(4, 'RENT_YOUR_STUFF'),
(4, 'MEATLESS'),
(4, 'SCREEN_TIME_LIMIT'),
(4, 'UNPLUGGED_ENTERTAINMENT');

-- For configuration_id = 5
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(5, 'SAVE_ON_UTILITIES'),
(5, 'RECYCLE'),
(5, 'CUT_CABLE'),
(5, 'PUBLIC_TRANSPORTATION');

-- For configuration_id = 6
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(6, 'REDUCE_FAST_FOOD'),
(6, 'THRIFT_SHOPPING'),
(6, 'LIMIT_GROCERY_SHOPPING'),
(6, 'CANCEL_SUBSCRIPTIONS');

-- For configuration_id = 7
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(7, 'SELL_UNWANTED_ITEMS'),
(7, 'BUY_SECOND_HAND'),
(7, 'PACK_LUNCH'),
(7, 'MINIMAL_SHOPPING'),
(7, 'NO_SPENDING');

-- For configuration_id = 8
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(8, 'RENT_OUT_YOUR_POSSESSIONS'),
(8, 'VEGETARIAN_DIET'),
(8, 'LIMIT_SCREEN_TIME'),
(8, 'UNPLUGGED_ENTERTAINMENT');

-- For configuration_id = 9
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(9, 'SHARE_EXPENSES'),
(9, 'BUY_USED_ITEMS'),
(9, 'BRING_LUNCH'),
(9, 'MINIMIZE_SHOPPING');

-- For configuration_id = 10
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(10, 'NO_COFFEE'),
(10, 'NO_CAR'),
(10, 'SHORTER_SHOWER');

-- For configuration_id = 11
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(11, 'SPEND_LESS_ON_FOOD'),
(11, 'BUY_USED_CLOTHES'),
(11, 'LESS_SHOPPING'),
(11, 'DROP_SUBSCRIPTION');

-- For configuration_id = 12
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(12, 'SELL_SOMETHING'),
(12, 'BUY_USED'),
(12, 'EAT_PACKED_LUNCH'),
(12, 'STOP_SHOPPING'),
(12, 'ZERO_SPENDING');

-- For configuration_id = 13
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(13, 'RENT_YOUR_STUFF'),
(13, 'MEATLESS'),
(13, 'SCREEN_TIME_LIMIT'),
(13, 'UNPLUGGED_ENTERTAINMENT');

-- For configuration_id = 14
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(14, 'SAVE_ON_UTILITIES'),
(14, 'RECYCLE'),
(14, 'CUT_CABLE'),
(14, 'PUBLIC_TRANSPORTATION');

-- For configuration_id = 15
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(15, 'REDUCE_FAST_FOOD'),
(15, 'THRIFT_SHOPPING'),
(15, 'LIMIT_GROCERY_SHOPPING'),
(15, 'CANCEL_SUBSCRIPTIONS');

-- For configuration_id = 16
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(16, 'SELL_UNWANTED_ITEMS'),
(16, 'BUY_SECOND_HAND'),
(16, 'PACK_LUNCH'),
(16, 'MINIMAL_SHOPPING'),
(16, 'NO_SPENDING');

-- For configuration_id = 17
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(17, 'RENT_OUT_YOUR_POSSESSIONS'),
(17, 'VEGETARIAN_DIET'),
(17, 'LIMIT_SCREEN_TIME'),
(17, 'UNPLUGGED_ENTERTAINMENT');

-- For configuration_id = 18
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(18, 'SHARE_EXPENSES'),
(18, 'BUY_USED_ITEMS'),
(18, 'BRING_LUNCH'),
(18, 'MINIMIZE_SHOPPING');

-- For configuration_id = 19
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(19, 'NO_COFFEE'),
(19, 'NO_CAR'),
(19, 'SHORTER_SHOWER');

-- For configuration_id = 20
INSERT INTO configuration_challenges (configuration_id, challenge_type) VALUES 
(20, 'SPEND_LESS_ON_FOOD'),
(20, 'BUY_USED_CLOTHES'),
(20, 'LESS_SHOPPING'),
(20, 'DROP_SUBSCRIPTION');

-- (PASSWORD = John1)
INSERT INTO user (user_id, bankid_sub, banner_image, created_at, email, first_name, last_name, password, profile_image, role, subscription_level, configuration_id, point_id, streak_id, checking_account_bban, savings_account_bban) 
VALUES 
(1, NULL, NULL, NOW(), 'john-doe@example.com', 'John', 'Doe', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 1, 1, 1, 12073650567, 12097256355),
(2, NULL, NULL, NOW(), 'jane-smith@example.com', 'Jane', 'Smith', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 2, 2, 2, 12073650567, 12097256355),
(3, NULL, NULL, NOW(), 'david-jones@example.com', 'David', 'Jones', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 3, 3, 3, 12073650567, 12097256355),
(4, NULL, NULL, NOW(), 'emily-johnson@example.com', 'Emily', 'Johnson', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 4, 4, 4, 12073650567, 12097256355),
(5, NULL, NULL, NOW(), 'michael-brown@example.com', 'Michael', 'Brown', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 5, 5, 5, 12073650567, 12097256355),
(6, NULL, NULL, NOW(), 'olivia-williams@example.com', 'Olivia', 'Williams', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 6, 6, 6, 12073650567, 12097256355),
(7, NULL, NULL, NOW(), 'william-jackson@example.com', 'William', 'Jackson', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 7, 7, 7, 12073650567, 12097256355),
(8, NULL, NULL, NOW(), 'ava-taylor@example.com', 'Ava', 'Taylor', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 8, 8, 8, 12073650567, 12097256355),
(9, NULL, NULL, NOW(), 'james-anderson@example.com', 'James', 'Anderson', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 9, 9, 9, 12073650567, 12097256355),
(10, NULL, NULL, NOW(), 'sophia-martinez@example.com', 'Sophia', 'Martinez', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 10, 10, 10, 12073650567, 12097256355),
(11, NULL, NULL, NOW(), 'alexandra-thomas@example.com', 'Alexandra', 'Thomas', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 11, 11, 11, 12073650567, 12097256355),
(12, NULL, NULL, NOW(), 'ethan-rodriguez@example.com', 'Ethan', 'Rodriguez', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 12, 12, 12, 12073650567, 12097256355),
(13, NULL, NULL, NOW(), 'isabella-lopez@example.com', 'Isabella', 'Lopez', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 13, 13, 13, 12073650567, 12097256355),
(14, NULL, NULL, NOW(), 'noah-gonzalez@example.com', 'Noah', 'Gonzalez', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 14, 14, 14, 12073650567, 12097256355),
(15, NULL, NULL, NOW(), 'mia-fernandez@example.com', 'Mia', 'Fernandez', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 15, 15, 15, 12073650567, 12097256355),
(16, NULL, NULL, NOW(), 'liam-gomez@example.com', 'Liam', 'Gomez', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 16, 16, 16, 12073650567, 12097256355),
(17, NULL, NULL, NOW(), 'emily-perez@example.com', 'Emily', 'Perez', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 17, 17, 17, 12073650567, 12097256355),
(18, NULL, NULL, NOW(), 'logan-morales@example.com', 'Logan', 'Morales', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 18, 18, 18, 12073650567, 12097256355),
(19, NULL, NULL, NOW(), 'amelia-rivera@example.com', 'Amelia', 'Rivera', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 19, 19, 19, 12073650567, 12097256355),
(20, NULL, NULL, NOW(), 'ethan-cook@example.com', 'Ethan', 'Cook', '$2a$10$j3.TmUIfByIa8ZfDksVb0OFzyTxxIo1jgCx59oO0rX67b7IB8cStq', NULL, 'USER', 'DEFAULT', 20, 20, 20, 12073650567, 12097256355);

-- Inserting friends
INSERT INTO friend (user_id, friend_id, pending, created_at) 
VALUES 
(1, 2, true, NOW()),
(1, 3, false, NOW()),
(1, 4, false, NOW()),
(1, 6, true, NOW()),
(1, 7, false, NOW()),
(1, 9, true, NOW()),
(1, 10, false, NOW()),
(1, 12, false, NOW()),
(1, 14, false, NOW()),
(1, 16, false, NOW()),
(1, 17, true, NOW()),
(1, 18, false, NOW()),
(1, 19, false, NOW()),
(1, 20, false, NOW()),
(2, 3, false, NOW()),
(2, 4, false, NOW()),
(2, 5, false, NOW()),
(2, 7, false, NOW()),
(2, 8, false, NOW()),
(2, 10, false, NOW()),
(2, 11, true, NOW()),
(2, 12, false, NOW()),
(2, 13, false, NOW()),
(2, 14, true, NOW()),
(2, 15, false, NOW()),
(2, 16, false, NOW()),
(2, 17, false, NOW()),
(2, 19, false, NOW()),
(2, 20, false, NOW()),
(3, 4, false, NOW()),
(3, 6, true, NOW()),
(3, 7, false, NOW()),
(3, 8, false, NOW()),
(3, 10, false, NOW()),
(3, 11, false, NOW()),
(3, 12, false, NOW()),
(3, 14, false, NOW()),
(3, 15, false, NOW()),
(3, 17, false, NOW()),
(3, 19, false, NOW()),
(3, 20, false, NOW()),
(4, 6, false, NOW()),
(4, 7, false, NOW()),
(4, 8, true, NOW()),
(4, 9, false, NOW()),
(4, 10, false, NOW()),
(4, 12, false, NOW()),
(4, 13, true, NOW()),
(4, 14, false, NOW()),
(4, 15, false, NOW()),
(4, 17, false, NOW()),
(4, 18, false, NOW()),
(4, 19, false, NOW()),
(4, 20, false, NOW()),
(5, 6, false, NOW()),
(5, 7, false, NOW()),
(5, 8, true, NOW()),
(5, 9, false, NOW()),
(5, 10, false, NOW()),
(5, 11, true, NOW()),
(5, 13, true, NOW()),
(5, 14, false, NOW()),
(5, 17, false, NOW()),
(5, 18, false, NOW()),
(5, 19, false, NOW()),
(5, 20, false, NOW()),
(6, 7, false, NOW()),
(6, 8, true, NOW()),
(6, 9, false, NOW()),
(6, 11, true, NOW()),
(6, 12, false, NOW()),
(6, 13, false, NOW()),
(6, 14, false, NOW()),
(6, 15, false, NOW()),
(6, 16, false, NOW()),
(6, 17, true, NOW()),
(6, 18, false, NOW()),
(6, 19, false, NOW()),
(6, 20, false, NOW()),
(7, 8, false, NOW()),
(7, 9, false, NOW()),
(7, 10, false, NOW()),
(7, 11, false, NOW()),
(7, 13, false, NOW()),
(7, 15, false, NOW()),
(7, 16, false, NOW()),
(7, 17, false, NOW()),
(7, 18, false, NOW()),
(7, 19, false, NOW()),
(7, 20, true, NOW()),
(8, 9, false, NOW()),
(8, 10, false, NOW()),
(8, 11, false, NOW()),
(8, 12, false, NOW()),
(8, 13, true, NOW()),
(8, 14, true, NOW()),
(8, 15, false, NOW()),
(8, 17, false, NOW()),
(8, 19, false, NOW()),
(8, 20, false, NOW()),
(9, 10, false, NOW()),
(9, 11, false, NOW()),
(9, 13, true, NOW()),
(9, 14, false, NOW()),
(9, 15, true, NOW()),
(9, 17, false, NOW()),
(9, 18, false, NOW()),
(9, 19, false, NOW()),
(9, 20, true, NOW()),
(10, 11, true, NOW()),
(10, 12, false, NOW()),
(10, 13, false, NOW()),
(10, 14, false, NOW()),
(10, 15, false, NOW()),
(10, 16, true, NOW()),
(10, 17, false, NOW()),
(10, 18, false, NOW()),
(10, 19, false, NOW()),
(11, 12, false, NOW()),
(11, 13, false, NOW()),
(11, 14, false, NOW()),
(11, 15, false, NOW()),
(11, 16, true, NOW()),
(11, 17, false, NOW()),
(11, 18, true, NOW()),
(11, 19, false, NOW()),
(11, 20, false, NOW()),
(12, 14, true, NOW()),
(12, 15, false, NOW()),
(12, 16, true, NOW()),
(12, 17, false, NOW()),
(12, 18, false, NOW()),
(12, 19, false, NOW()),
(12, 20, false, NOW()),
(13, 14, false, NOW()),
(13, 15, true, NOW()),
(13, 16, true, NOW()),
(13, 17, false, NOW()),
(13, 18, false, NOW()),
(13, 19, false, NOW()),
(14, 15, false, NOW()),
(14, 16, false, NOW()),
(14, 17, true, NOW()),
(14, 19, true, NOW()),
(14, 20, false, NOW()),
(15, 17, false, NOW()),
(15, 18, false, NOW()),
(15, 19, true, NOW()),
(15, 20, false, NOW()),
(16, 17, true, NOW()),
(16, 19, false, NOW()),
(16, 20, false, NOW()),
(17, 18, false, NOW()),
(17, 19, false, NOW()),
(17, 20, false, NOW()),
(18, 20, false, NOW()),
(19, 20, true, NOW());

INSERT INTO challenge_template(challenge_template_id, challenge_name, challenge_text, challenge_amount, challenge_type) 
VALUES 
(1, 'Kjøp billig kaffe', 'Spar {unit_amount} hver gang du kjøper kaffe ved å velge en billigere variant. Totalt {checkDays} kjøp billig kaffe over en periode på {totalDays} dager for å spare {total_amount} kr.', 20, 'NO_COFFEE'), 
(2, 'Selg ubrukte ting', 'Selg ting som du ikke har brukt på ett år. Tjen {unit_amount} kroner {checkDays} ganger for å tjene på ubrukte ting. Gjennom en periode på {totalDays} dager sparer du {total_amount} kroner.', 25, 'SELL_SOMETHING'), 
(3, 'Lag hjemmelaget lunsj', 'Spar {unit_amount} hver gang du lager lunsj hjemme og spar totalt {total_amount} kroner ved å ikke kjøpe lunsj {checkDays} ganger over en periode på {totalDays} dager.', 35, 'EAT_PACKED_LUNCH'), 
(4, 'Dusj kortere', 'Spar {unit_amount} hver gang du dusjer kortere {checkDays} ganger og spar totalt {total_amount} kroner over en periode på {totalDays} dager.', 15, 'SHORTER_SHOWER'), 
(5, 'Spis vegetar', 'Din utfordring er å spare {unit_amount} kroner ved å spise vegetar {checkDays} ganger og spar totalt {total_amount} kr ved å lage vegetariske retter over {totalDays} dager.', 50, 'MEATLESS'), 
(6, 'Kjøp bruktvarer', 'Utfordre deg selv med å kjøpe brukte varer hos din bruktleverandør og spar {unit_amount} kroner {checkDays} ganger og spar totalt {total_amount} over {totalDays} dager.', 40, 'BUY_USED'), 
(7, 'Bruk alternative transportmidler', 'Ikke bruk bilen, bruk heller billigere transportmidler som sykling, gåing eller buss {checkDays} ganger og spar {unit_amount} kroner. Dette gjøres over {totalDays} dager og kan spare {total_amount} kroner.', 40, 'NO_CAR'), 
(8, 'Lag kaffe hjemme', 'Hvis du velger å lage kaffe hjemme i stedet for å kjøpe det ute, kan du spare {unit_amount} kr. Gjør dette {checkDays} ganger over en periode på {totalDays} dager for å spare {total_amount} kr.', 30, 'NO_COFFEE'), 
(9, 'Ta kortere dusjer', 'Lange dusjer krever mye vann, og mye vann koster mye penger. Hvis du bruker 10 minutter per dusj, kan du spare {unit_amount} kr. Ta kortere dusjer {checkDays} ganger over en periode på {totalDays} dager for å spare {total_amount} kr.', 30, 'SHORTER_SHOWER'), 
(10, 'Kjøp brukte klær', 'Kjøp klær fra bruktbutikk for å spare penger. Hver gang du kjøper et nytt plagg, kan du spare {unit_amount} kr. Gjør dette {checkDays} ganger over en periode på {totalDays} dager for å spare {total_amount} kr.', 230, 'BUY_USED_CLOTHES'), 
(11, 'Kjøp mindre klær', 'Slutt å bruk penger på klær, du har nok av det. Hver gang du kjøper klær, kan du spare {unit_amount} kr ved å heller ikke kjøpe dem. Du skal ikke kjøpe klær {checkAmount} ganger i løpet av {totalDays} dager for å spare {total_amount} kr.', 600, 'LESS_SHOPPING'), 
(12, 'Kjøp billigere mat', 'I stedet for å bruke penger på dyre matvarer kan du gå over til billigere varianter, for eksempel First Price, Prima eller Extra. Kjøp billigere mat {checkAmount} ganger i løpet av {totalDays} dager for å spare {total_amount} kr.', 100, 'SPEND_LESS_ON_FOOD'), 
(13, 'Kjøp utgått mat', 'Bruk tjenester som "Too Good To Go" for å kjøpe mat som ellers ville blitt kastet. Spar {unit_amount} hver gang du bruker "Too Good To Go". Bruk "Too Good To Go" {checkDays} ganger over en periode på {totalDays} dager for å spare {total_amount} kr.', 80, 'SPEND_LESS_ON_FOOD');