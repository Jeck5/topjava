DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, calories, description, datetime) VALUES
  (100000, 500, 'завтрак', TIMESTAMP '2018-03-17 10:00:00'),
  (100000, 1000, 'обед', TIMESTAMP '2018-03-17 14:00:00'),
  (100000, 501, 'ужин', TIMESTAMP '2018-03-17 20:00:00'),
  (100000, 500, 'завтрак', TIMESTAMP '2018-03-16 10:00:00'),
  (100000, 500, 'обед', TIMESTAMP '2018-03-16 14:00:00'),
  (100000, 500, 'ужин', TIMESTAMP '2018-03-16 19:31:00'),
  (100000, 500, 'завтрак', TIMESTAMP '2018-03-15 10:00:00'),
  (100000, 500, 'обед', TIMESTAMP '2018-03-15 14:44:00'),
  (100001, 500, 'завтрак', TIMESTAMP '2018-03-17 10:00:00'),
  (100001, 500, 'обед', TIMESTAMP '2018-03-17 14:00:00'),
  (100001, 1501, 'завтрак', TIMESTAMP '2018-03-14 09:40:00'),
  (100001, 500, 'обед', TIMESTAMP '2018-03-14 14:30:00');