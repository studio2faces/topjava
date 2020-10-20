DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);


INSERT INTO meals(datetime, description, calories, user_id)
VALUES ('2020-01-30 10:00', 'Breakfast', 500, 100000),
       ('2020-01-30 14:00', 'Lunch', 1000, 100000),
       ('2020-01-30 20:00', 'Dinner', 500, 100000),
       ('2020-01-31 10:00', 'Breakfast', 500, 100001),
       ('2020-01-31 12:00', 'Brunch', 200, 100001),
       ('2020-01-31 14:00', 'Lunch', 1000, 100001),
       ('2020-01-31 20:00', 'Dinner', 500, 100001);