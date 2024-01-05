create table if not exists accidents
(
    id serial primary key,
    name varchar,
    text varchar,
    address varchar,
    accident_types_id int not null references accident_types (id)
    );