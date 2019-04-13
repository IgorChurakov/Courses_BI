CREATE TABLE organization (
  id bigint primary key auto_increment,
  version bigint default 0,
  name varchar(256) not null,
  full_name varchar(256) not null,
  inn varchar(256) not null,
  kpp varchar(256) not null,
  address varchar(256) not null,
  phone varchar(256),
  is_active boolean default true
);

CREATE TABLE office (
  id bigint primary key auto_increment,
  version bigint default 0,
  org_id bigint references organization(id),
  name varchar(256) not null,
  address varchar(256) not null,
  phone varchar(256),
  is_active boolean default true
);

CREATE TABLE countries (
  code bigint primary key,
  version bigint default 0,
  name varchar(256) not null
);

CREATE TABLE docs (
  code bigint primary key,
  version bigint default 0,
  name varchar(256) not null
);

CREATE TABLE users (
  id bigint primary key auto_increment,
  version bigint default 0,
  office_id bigint references office(id),
  first_name varchar(256) not null,
  second_name varchar(256),
  middle_name varchar(256),
  position varchar(256) not null,
  phone varchar(256),
  doc_code bigint references docs(code),
  doc_name varchar(256),
  doc_number varchar(256),
  doc_date date,
  citizenship_code bigint references countries(code),
  is_identified boolean
);