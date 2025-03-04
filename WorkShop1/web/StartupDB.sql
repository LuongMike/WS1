CREATE DATABASE StartupDB
GO 

USE StartupDB
GO

CREATE TABLE tblUsers (
    Username VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role VARCHAR(20) NOT NULL
);
GO

INSERT INTO tblUsers (Username, Name, Password, Role) VALUES
('user1', 'Alice Johnson', '123456', 'Founder'),
('user2', 'Bob Smith', '123456', 'Team Member'),
('user3', 'Charlie Brown', '123456', 'Founder'),
('user4', 'David Wilson', '123456', 'Team Member'),
('user5', 'Emma Watson', '123456', 'Founder'),
('user6', 'Frank Castle', '123456', 'Team Member'),
('user7', 'Grace Lee', '123456', 'Founder'),
('user8', 'Henry Adams', '123456', 'Team Member'),
('user9', 'Ivy Green', '123456', 'Founder'),
('user10', 'Jack Black', '123456', 'Team Member');

CREATE TABLE tblStartupProjects (
    project_id INT IDENTITY(1,1) PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    Description TEXT,
    Status VARCHAR(20) NOT NULL,
    estimated_launch DATE NOT NULL
);

INSERT INTO tblStartupProjects (project_name, Description, Status, estimated_launch) VALUES
('AI Assistant', 'AI-powered chatbot', 'Ideation', '2025-06-01'),
('Smart Trash Bin', 'IoT-based waste management', 'Development', '2025-07-15'),
('Crypto Wallet', 'Secure cryptocurrency wallet', 'Launch', '2025-09-20'),
('Green Energy App', 'Renewable energy tracker', 'Scaling', '2025-10-05'),
('Online Learning Hub', 'E-learning platform', 'Ideation', '2025-08-12'),
('Health Tracker', 'Wearable health monitor', 'Development', '2025-11-03'),
('Social Media 2.0', 'Privacy-focused social app', 'Launch', '2025-12-10'),
('Smart Home System', 'IoT-based home automation', 'Scaling', '2026-01-25'),
('VR Fitness Game', 'Virtual reality workout', 'Development', '2026-02-18'),
('Blockchain Voting', 'Secure online voting system', 'Ideation', '2026-03-05');
GO