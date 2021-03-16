INSERT INTO roles (created_time, created_by, updated_time, updated_by, name, enabled)
VALUES ('2021-03-15 22:39:34.000000', 1, '2021-03-15 22:40:47.000000', 1, 'Root', true),
       ('2021-03-15 22:39:34.000000', 1, '2021-03-15 22:40:47.000000', 1, 'Admin', true),
       ('2021-03-15 22:39:34.000000', 1, '2021-03-15 22:40:47.000000', 1, 'Manager', true),
       ('2021-03-15 22:39:34.000000', 1, '2021-03-15 22:40:47.000000', 1, 'Employee', true);

INSERT INTO users (created_by, created_time, enabled, updated_by, updated_time, email, first_name, last_name,
                   password, phone, username, role_id)
VALUES (1, '2021-03-15 22:39:34.000000', true, 1, '2021-03-15 22:40:47.000000', 'ruslan@ruslan.com', 'Ruslan',
        'Kasymov', '$2a$10$hBYUooYi.TWl.3A3ia2zU.RrgE/EZSPNvqLmWKwjK.jRjIXoE84hO', '432-434-3443', 'ruslan', 1);