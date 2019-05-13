CREATE TABLE organization (               --Table of Organizations
  id bigint primary key auto_increment,   --Organization's ID
  version bigint default 0,               --Hibernate service field
  name varchar(256) not null,             --Organization's Name
  full_name varchar(256) not null,        --Organization's Full Name
  inn varchar(64) not null,               --Organization's INN
  kpp varchar(64) not null,               --Organization's KPP
  address varchar(256) not null,          --Organization's Address
  phone varchar(256),                     --Organization's Phone
  is_active boolean                       --Organization's Active State
);

CREATE TABLE office (                     --Table of Offices
  id bigint primary key auto_increment,   --Office's ID
  version bigint default 0,               --Hibernate service field
  org_id bigint not null,                 --Office's Organization Owner ID
  name varchar(256) not null,             --Office's Name
  address varchar(256) not null,          --Office's Address
  phone varchar(256),                     --Office's Phone
  is_active boolean                       --Office's Active State
);

CREATE TABLE countries (                  --Countries Catalogue
  id bigint primary key auto_increment,   --Country ID
  version bigint default 0,               --Hibernate service field
  code integer not null,                  --Country Code
  name varchar(256) not null              --Country Name
);

CREATE TABLE doc_types (                  --Document Types Catalogue
  id bigint primary key auto_increment,   --DocType's ID
  version bigint default 0,               --Hibernate service field
  code integer not null,                  --DocType's Code
  name varchar(256) not null              --DocType's Name
);

CREATE TABLE document (                   --Table of Documents
  id bigint primary key auto_increment,   --Document's ID
  version bigint default 0,               --Hibernate service field
  doc_type bigint not null,               --Document's Type references doc_type(id)
  doc_number varchar(64) not null,        --Document's Number
  doc_date date not null                  --Document's Date
);

CREATE TABLE users (                      --Table of Users
  id bigint primary key auto_increment,   --User's ID
  version bigint default 0,               --Hibernate Service Field
  office_id bigint not null,              --User's Current Office ID references office(id)
  first_name varchar(256) not null,       --User's First Name
  second_name varchar(256),               --User's Second Name
  middle_name varchar(256),               --User's Middle Name
  position varchar(256) not null,         --User's Position
  phone varchar(256),                     --User's Phone
  doc_code bigint,                        --User's Document references document(id)
  citizenship_code bigint,                --User's Citizenship references countries(id)
  is_identified boolean                   --User's Identification State
);

create index IX_Office_Organization_Id on office(org_id);
alter table office add foreign key (org_id) references organization(id);

create index IX_Document_Doc_Type on document(doc_type);
alter table document add foreign key (doc_type) references doc_types(id);

create index IX_Users_Office_Id on users(office_id);
alter table users add foreign key (office_id) references office(id);

create index IX_Users_Doc_Code on users(doc_code);
alter table users add foreign key (doc_code) references document(id);

create index IX_Users_Citizenship_Code on users(citizenship_code);
alter table users add foreign key (citizenship_code) references countries(id);