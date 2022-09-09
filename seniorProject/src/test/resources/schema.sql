drop table if exists friend CASCADE;
drop table if exists location_info CASCADE;
drop table if exists map CASCADE;
drop table if exists map_small_subject CASCADE;
drop table if exists user_map CASCADE;
drop table if exists users CASCADE;

create table users (
    user_id varchar(255) not null,
    created_at timestamp,
    modified_at timestamp,
    name varchar(255),
    password varchar(255),
    primary key (user_id)
);

create table friend (
    friend_id varchar(255) not null,
    user_id varchar(255) not null,
    primary key (friend_id, user_id),
    foreign key (friend_id) references users,
    foreign key (user_id) references users
);

create table map (
    map_id bigint not null,
    created_at timestamp,
    modified_at timestamp,
    created_by varchar(255),
    modified_by varchar(255),
    name varchar(255),
    password varchar(255),
    primary key (map_id)
);
create table map_small_subject (
    small_subject_id bigint not null,
    created_at timestamp,
    modified_at timestamp,
    created_by varchar(255),
    modified_by varchar(255),
    small_subject varchar(255),
    map_id bigint,
    primary key (small_subject_id),
    foreign key (map_id) references map
);

create table user_map (
    map_id bigint not null,
    user_id varchar(255) not null,
    visibility varchar(255),
    primary key (map_id, user_id),
    foreign key (map_id) references map,
    foreign key (user_id) references users
);
create table location_info (
    location_id bigint not null,
    created_at timestamp,
    modified_at timestamp,
    created_by varchar(255),
    modified_by varchar(255),
    big_subject varchar(255),
    latitude double,
    longitude double,
    memo varchar(255),
    name varchar(255),
    map_id bigint,
    small_subject_id bigint,
    primary key (location_id),
    foreign key (map_id) references map,
    foreign key (small_subject_id) references map_small_subject
);