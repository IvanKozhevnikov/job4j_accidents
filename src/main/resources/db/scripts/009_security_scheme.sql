CREATE TABLE authorities (
                             id serial primary key,
                             username varchar(50) not null,
                             authority VARCHAR(50) NOT NULL
);

CREATE TABLE author (
                             id serial primary key,
                             authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
                       id serial primary key,
                       username VARCHAR(50) NOT NULL unique,
                       password VARCHAR(100) NOT NULL,
                       enabled boolean default true,
                       author_id int not null references author(id)
);

insert into author (authority) values ('ROLE_USER');
insert into author (authority) values ('ROLE_ADMIN');

insert into authorities (username, authority) values ('ivan@ya.ru', 'ROLE_USER');

insert into users (username, enabled, password, author_id)
values ('root', true, '$2a$10$wY1twJhMQjGVxv4y5dBC5ucCBlzkzT4FIGa4FNB/pS9GaXC2wm9/W',
        (select id from author where authority = 'ROLE_ADMIN'));