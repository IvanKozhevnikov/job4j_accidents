CREATE TABLE accident_types(
                              id serial primary key,
                              name text not null unique
);

insert into accident_types(name) values ('Две машины');
insert into accident_types(name) values ('Машина и человек');
insert into accident_types(name) values ('Машина и велосипед');

create table if not exists accidents
(
    id serial primary key,
    name varchar,
    text varchar,
    address varchar,
    accident_types_id int not null references accident_types (id)
    );

insert into accidents (name, text, address, accident_types_id) values ('Accident 1', 'Парковка в неположенном месте', 'ул. Гагарина д.9', 1);
insert into accidents (name, text, address, accident_types_id) values ('Accident 2', 'Отключены габаритные огни', 'ул. Советская д.11', 2);
insert into accidents (name, text, address, accident_types_id) values ('Accident 3', 'Не уступил дорогу при помехе с права', 'ул. Винокурова д.15', 3);

CREATE TABLE rules(
                      id serial primary key,
                      name text not null unique
);

insert into rules(name) values('Статья 1');
insert into rules(name) values('Статья 2');
insert into rules(name) values('Статья 3');

CREATE TABLE accident_rules(
                               id serial primary key,
                               accident_id int references accident(id),
                               rule_id int references rules(id)
);

insert into accident_rules(accident_id, rule_id) values (1, 2);
insert into accident_rules(accident_id, rule_id) values (1, 2);
insert into accident_rules(accident_id, rule_id) values (1, 2);