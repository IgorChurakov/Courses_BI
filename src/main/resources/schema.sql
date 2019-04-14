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
  org_id bigint not null,
  name varchar(256) not null,
  address varchar(256) not null,
  phone varchar(256),
  is_active boolean default true
);

CREATE TABLE countries (
  id bigint primary key,
  version bigint default 0,
  code integer not null,
  name varchar(256) not null
);

CREATE TABLE docs (
  id bigint primary key,
  version bigint default 0,
  code integer not null,
  name varchar(256) not null
);

CREATE TABLE document (
  id bigint primary key,
  version bigint default 0,
  doc_type bigint not null,
  user_id bigint not null,
  doc_number varchar(10) not null,
  doc_date date not null
);

CREATE TABLE users (
  id bigint primary key auto_increment,
  version bigint default 0,
  office_id bigint not null,
  first_name varchar(256) not null,
  second_name varchar(256),
  middle_name varchar(256),
  position varchar(256) not null,
  phone varchar(256),
  doc_code bigint not null,
  citizenship_code bigint not null,
  is_identified boolean
);

create index IX_Office_Organization_Id on office(org_id);
alter table office add foreign key (org_id) references organization(id);

create index IX_Document_Doc_Type on document(doc_type);
alter table document add foreign key (doc_type) references docs(id);

create index IX_Document_User_Id on document(user_id);
alter table document add foreign key (user_id) references users(id);

create index IX_Users_Office_Id on users(office_id);
alter table users add foreign key (office_id) references office(id);

create index IX_Users_Doc_Code on users(doc_code);
alter table users add foreign key (doc_code) references document(id);

create index IX_Users_Citizenship_Code on users(citizenship_code);
alter table users add foreign key (citizenship_code) references countries(id);