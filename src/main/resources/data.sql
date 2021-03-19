INSERT INTO roles (created_time, created_by, updated_time, updated_by, name, enabled)
VALUES ('2021-03-15 22:39:34.000000', 1, '2021-03-15 22:40:47.000000', 1, 'Root', true),
       ('2021-03-15 22:39:34.000000', 1, '2021-03-15 22:40:47.000000', 1, 'Admin', true),
       ('2021-03-15 22:39:34.000000', 1, '2021-03-15 22:40:47.000000', 1, 'Manager', true),
       ('2021-03-15 22:39:34.000000', 1, '2021-03-15 22:40:47.000000', 1, 'Employee', true);

INSERT INTO company (created_by, created_time, enabled, updated_by, updated_time, address1, address2, email,
                     establishment_date, representative, state, title, zip)
VALUES (1, '2021-03-15 22:39:34.000000', true, 1, '2021-03-15 22:39:34.000000', 'Address1', 'Address2',
        'company@addrs', '2021-03-15 22:39:34.000000', 'Admin1', 'FLORIDA', 'First Company', '12345'),
       (1, '2021-03-15 22:39:34.000000', true, 1, '2021-03-15 22:39:34.000000', 'Address1', 'Address2',
        'company2@addrs', '2021-03-15 22:39:34.000000', 'Admin2', 'MISSOURI', 'Second Company', '12345');

INSERT INTO users (created_by, created_time, enabled, updated_by, updated_time, email, first_name, last_name,
                   password, phone, username, role_id, company_id)
VALUES (1, '2021-03-15 22:39:34.000000', true, 1, '2021-03-15 22:40:47.000000', 'ruslan@ruslan.com', 'Ruslan',
        'Kasymov', '$2a$10$hBYUooYi.TWl.3A3ia2zU.RrgE/EZSPNvqLmWKwjK.jRjIXoE84hO', '432-434-3443', 'ruslan', 1, 1),
       (1, '2021-03-15 22:39:34.000000', true, 1, '2021-03-15 22:40:47.000000', 'root@root', 'root',
        'root', '$2a$10$5Nuuddm8lpcc6TGnXqk0p.gqbMiGOlcLF7iK186dVexo6Cf5VBxJ2', '432-434-3443', 'root', 1, 2),
       (1, '2021-03-15 22:39:34.000000', true, 1, '2021-03-15 22:40:47.000000', 'admin@admin', 'admin',
        'admin', '$2a$10$BQeNFG3DGhqELugLJ97gC.S3OgdVVe52w2CWqUKFn5DLPf8vlWJbO', '432-434-3443', 'admin', 2, 2),
       (1, '2021-03-15 22:39:34.000000', true, 1, '2021-03-15 22:40:47.000000', 'manager@manager', 'manager',
        'manager', '$2a$10$aNFRAioXHHM3zte/Tl3v6.a4vubTFHHiHnWZNsTk/bhdXWCl/9ZqS', '432-434-3443', 'manager', 3, 2),
       (1, '2021-03-15 22:39:34.000000', true, 1, '2021-03-15 22:40:47.000000', 'user@user', 'user',
        'user', '$2a$10$heeyJYppVmqAQ..xAGeLx.FFXV3dnINF0t5LF/01TV2Wecpyp0rf2', '432-434-3443', 'user', 4, 2);

