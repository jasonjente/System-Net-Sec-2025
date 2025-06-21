-- Insert admin user (username: admin, password: admin123)
INSERT INTO users (id, first_name, last_name, username, password)
VALUES (1, 'System', 'Admin', 'admin', '$2a$10$O4TmkGVX9QtnCDx5UusUauNPx7ZynEdkn4K7UzjLa6KD5V9MiLVwK');

-- Insert student user (username: AM123456, password: mysecurepass)
INSERT INTO users (id, first_name, last_name, username, password)
VALUES (2, 'YourName', 'YourLastName', 'AM123456', '$2a$10$V1xeoIVDTKmHRzJyoDsmveYFxHcBkgfRnpCfpqWm0G7w0UbTSq5.2');
