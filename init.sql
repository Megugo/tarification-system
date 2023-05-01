CREATE DATABASE "SRM-Users";
CREATE DATABASE "BRT";

\connect BRT;

create table tariff
(
    id                              varchar(255) not null
        primary key,
    base_price                      double precision,
    default_incoming_price          double precision,
    default_outgoing_price          double precision,
    is_special_minutes_splitted     boolean,
    special_minutes_incoming_amount integer,
    special_minutes_incoming_price  double precision,
    special_minutes_outgoing_amount integer,
    special_minutes_outgoing_price  double precision
);

alter table tariff
    owner to postgres;

INSERT INTO Tariff (id, base_price, default_incoming_price, default_outgoing_price, is_special_minutes_splitted, special_minutes_incoming_amount, special_minutes_incoming_price, special_minutes_outgoing_amount, special_minutes_outgoing_price)
VALUES (06, 100, 1, 1, false, 300, 0, 300, 0),
       (03, 0, 1.5, 1.5, false, 0, 0, 0, 0),
       (11, 0, 0, 1.5, true, 0, 0, 100, 0.5);