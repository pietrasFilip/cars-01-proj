use test_cars_01;

create table if not exists components_data (
    id integer primary key auto_increment,
    name varchar(50) not null
);

create table if not exists cars (
    id integer primary key auto_increment,
    model varchar(50) not null,
    price decimal,
    color varchar(50) not null,
    mileage integer
);

create table if not exists cars_data (
    car_id integer not null,
    component_id integer not null,
    primary key (car_id, component_id),
    foreign key (car_id) references cars(id) on delete cascade on update cascade,
    foreign key (component_id) references components_data(id) on delete cascade on update cascade
);

create table if not exists users (
    id integer primary key auto_increment,
    username varchar(50) not null,
    email varchar(50) not null,
    password varchar(512) not null,
    role varchar(50) not null,
    is_active bool default false
);