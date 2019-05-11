INSERT INTO organization(name, full_name, inn, kpp, address, phone, is_active)
VALUES ('Planet Express','Planet Express, Inc.','24135846856461635384','133769322','USA, NNY St., New New York, Main st., 13','+79631596428',true),
       ('World of Wonder','World of Wonder, Inc.','68464654138496876123','694555228','Russian Federation, Moscow st., Moscow, Lenina st., 35','+79245613298',true);


INSERT INTO office(org_id,name,address,phone,is_active)
VALUES (1,'The swaggy offcie','USA, NNY St., New New York, Main st., 13','+79653215645',true),
       (1,'The secretary room','USA, NNY St., New New York, Main st., 13','+79642135456',true),
       (2,'Office of Fantasies','Russian Federation, Moscow st., Moscow, Lenina st., 35','+79542368954',true),
       (1,'The couriers shack','USA, NNY St., New New York, Main st., 13','+79651236458',true);

INSERT INTO countries(code,name)
VALUES (643,'Russian Federation'),
       (800,'Uganda'),
       (804,'Ukraine'),
       (752,'Sweden'),
       (192,'Cuba'),
       (004,'Afghanistan'),
       (756,'Switzerland'),
       (840,'United States of America');

INSERT INTO doc_types(code,name)
VALUES (21,'Паспорт гражданина Российской Федерации'),
       (10,'Паспорт иностранного гражданина'),
       (12,'Вид на жительство в Российской Федерации'),
       (03,'Свидетельство о рождении'),
       (07,'Военный билет');

INSERT INTO document(doc_type,doc_number,doc_date,user_id)
VALUES (1,'6319586645',TO_DATE('25-03-2019','DD-MM-YYYY'),1),
       (1,'6310569658',TO_DATE('16-10-2010','DD-MM-YYYY'),2),
       (2,'9615123548',TO_DATE('16-08-2015','DD-MM-YYYY'),3),
       (2,'8514369458',TO_DATE('25-01-2014','DD-MM-YYYY'),4),
       (2,'7612698452',TO_DATE('08-09-2012','DD-MM-YYYY'),5),
       (2,'9618654852',TO_DATE('20-12-2018','DD-MM-YYYY'),6);

INSERT INTO users(office_id,first_name,second_name,middle_name,position,phone,doc_code,citizenship_code,is_identified)
VALUES (1,'Alexey','Ivanov','Denisovich','Director','+79274569897',1,1,true),
       (2,'Marina','Mironova','Alexeevna','Secretary','+79276541312',2,1,true),
       (4,'Peter','Parker','Solomon','Courier','+79624861536',3,8,true),
       (3,'John','Cena','J','Director','+79876541236',4,4,true),
       (3,'Steven','Rambo','Nocks','Advisor','+79315698745',5,7,true),
       (4,'Yuri','Gagarin','Alekseyevich','Head Courier','+79653652528',6,1,true);

CREATE INDEX IF NOT EXISTS IX_Document_User_Id on document(user_id);
alter table document add foreign key (user_id) references users(id);