create table customer (
    id uuid not null primary key,
    name text
);

create table trip (
    id uuid not null primary key,
    available_spots integer not null,
    title text,
    created_date timestamp,
    updated_date timestamp
);

create table reservation (
    id uuid not null DEFAULT RANDOM_UUID() primary key,
    booking_id uuid not null,
    trip_id uuid not null REFERENCES trip(id),
    customer_id uuid not null REFERENCES customer(id),
    status text not null,
    created_date timestamp,
    updated_date timestamp
);

create table spot (
    id uuid not null primary key,
    title text,
    trip_id uuid not null REFERENCES trip(id),
    is_reserved bool default false,
    created_date timestamp,
    updated_date timestamp
);

create table reservation_spot(
    id uuid not null primary key,
    spot_id uuid not null REFERENCES spot(id),
    reservation_id uuid not null REFERENCES reservation(id),
    status text not null,
    created_date timestamp
);


-- Dummy Data
insert into customer (id, name) values ('16a2ae47-6b8e-4c70-8439-710eab527ea3', 'John');

insert into trip (id, available_spots) values ('1b4c80b2-ffc6-45bb-bf00-29afc74fc29d', 5);

insert into spot (id, trip_id) values ('f413da57-348f-483f-9afb-951981395f48','1b4c80b2-ffc6-45bb-bf00-29afc74fc29d');
insert into spot (id, trip_id) values ('97483871-0710-4638-94df-fd9072e2e1da','1b4c80b2-ffc6-45bb-bf00-29afc74fc29d');
insert into spot (id, trip_id) values ('67f5a6ef-ba5f-45c9-b577-444a7001c62a','1b4c80b2-ffc6-45bb-bf00-29afc74fc29d');
insert into spot (id, trip_id) values ('52716b59-427e-445b-97bb-312bf3d91cc7','1b4c80b2-ffc6-45bb-bf00-29afc74fc29d');
insert into spot (id, trip_id) values ('17d6d0c8-31ca-4d72-ae5b-907a4b19122f','1b4c80b2-ffc6-45bb-bf00-29afc74fc29d');