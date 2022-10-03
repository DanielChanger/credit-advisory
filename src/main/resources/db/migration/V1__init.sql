create table if not exists users
(
    id       serial primary key,
    username text not null unique,
    email    text not null unique
);

create type advisor_role as enum ('associate', 'partner', 'senior');
create table if not exists advisors
(
    id         bigint       not null unique,
    first_name text         not null,
    last_name  text         not null,
    role       advisor_role not null,
    foreign key (id) references users (id)
);

create table if not exists applicants
(
    id         bigint not null unique,
    first_name text   not null,
    last_name  text   not null,
    ssn        text   not null,
    city       text   not null,
    street     text   not null,
    number     text   not null,
    zip        text   not null,
    apt        text   not null,
    foreign key (id) references users (id)
);

create type application_status as enum ('new', 'assigned', 'on_hold', 'approved', 'cancelled', 'declined');
create table if not exists applications
(
    id           serial primary key,
    amount       double precision   not null,
    status       application_status not null,
    created_at   timestamp          not null default now(),
    assigned_at  timestamp,
    resolved_at  timestamp,
    applicant_id bigint             not null,
    advisor_id   bigint,
    foreign key (applicant_id) references applicants (id),
    foreign key (advisor_id) references advisors (id)
);

create type phone_type AS ENUM ('home', 'work', 'mobile');
create table if not exists phone_numbers
(
    number       text       not null unique,
    type         phone_type not null,
    applicant_id bigint     not null,
    foreign key (applicant_id) references applicants (id)
);


INSERT INTO users(username, email)
SELECT 'user' ||  id, 'user' || id || '@mail.com'
FROM generate_series(1,1000) id;

INSERT INTO advisors(id, first_name, last_name, role)
SELECT id,
       md5(random()::text),
       md5(random()::text),
       (select role FROM unnest(enum_range(NULL::advisor_role)) role ORDER BY random() LIMIT 1)
FROM generate_series(1, 1000) id;


INSERT INTO applicants(id, first_name, last_name, ssn, city, street, number, zip, apt)
SELECT id,
       md5(random()::text),
       md5(random()::text),
       md5(random()::text),
       md5(random()::text),
       md5(random()::text),
       md5(random()::text),
       md5(random()::text),
       md5(random()::text)
FROM generate_series(1, 1000) id;


insert into applications(amount, status, assigned_at, resolved_at, applicant_id, advisor_id)
select random()::double precision,
       (select status FROM unnest(enum_range(NULL::application_status)) status ORDER BY random() LIMIT 1),
       null,
       null,
       id,
       null
from generate_series(1, 500) id;

