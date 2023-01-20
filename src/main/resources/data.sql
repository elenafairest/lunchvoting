INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);


INSERT INTO RESTAURANT (name, contact_info)
VALUES ('KakaoMama', '+7 8483 201-221, shop@kakaomama.ru'),
       ('Olivka', 'olivks-tlt@mail.ru'),
       ('Cheshire Cat', '8 (848) 236-25-03');

INSERT INTO MENU_ITEM (name, price, menu_date, restaurant_id)
VALUES ('pumpkin cream-soup', 150, '2022-11-08', 1),
       ('fried salmon', 350, '2022-11-08', 1),
       ('grilled cheese', 170, '2022-11-08', 1),
       ('pasta carbonara', 250, '2022-11-08', 2),
       ('lasagne', 280, '2022-11-08', 2),
       ('grilled steak', 400, '2022-11-08', 3),
       ('caesar salad', 230, '2022-11-08', 3),
       ('mashroom cream-soup', 150, '2022-12-09', 1),
       ('fried chicken with potatoes', 250, '2022-12-09', 1),
       ('tuna salad', 200, '2022-12-09', 1),
       ('pizza margherita', 200, '2022-12-09', 2),
       ('caprese salad', 150, '2022-12-09', 2),
       ('fish and chips', 350, '2022-12-09', 3),
       ('greek salad', 200, '2022-12-09', 3),
       ('brokoli cream-soup', 150, CURRENT_DATE - 2, 1),
       ('meatball in tomato sauce', 300, CURRENT_DATE - 2, 1),
       ('potato salad', 150, CURRENT_DATE - 2, 1),
       ('risotto', 270, CURRENT_DATE, 2),
       ('stuffed eggplant', 250, CURRENT_DATE, 2),
       ('hamburger', 350, CURRENT_DATE, 3),
       ('paella', 300, CURRENT_DATE, 3);

INSERT INTO VOTE (restaurant_id, user_id, vote_date)
values (2, 1, '2022-11-08'),
       (1, 2, '2022-11-08'),
       (3, 1, '2022-12-09'),
       (2, 2, '2022-12-09'),
       (1, 1, CURRENT_DATE - 2),
       (2, 2, CURRENT_DATE);