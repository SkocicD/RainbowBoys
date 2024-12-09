delete from gymnast_classes;
delete from class_coaches;
delete from coaches;
delete from classes;
delete from gymnasts;

insert into gymnasts (first_name, last_name, birthdate) values ('david', 'skocic', '11-18-2003');
insert into gymnasts (first_name, last_name, birthdate) values ('david', 'skocic', '11-18-2003');
insert into gymnasts (first_name, last_name, birthdate) values ('alex', 'skocic', '11-15-2001');
insert into gymnasts (first_name, last_name, birthdate) values ('michael', 'smith', '1-1-2010');
insert into gymnasts (first_name, last_name, birthdate) values ('allen', 'bradford', '11-18-2015');
insert into gymnasts (first_name, last_name, birthdate) values ('mose', 'durham', '7-22-1999');
insert into gymnasts (first_name, last_name, birthdate) values ('stephan', 'Farrell', '11-18-2003');
insert into gymnasts (first_name, last_name, birthdate) values ('Leon', 'Patel', '11-18-2003');
insert into gymnasts (first_name, last_name, birthdate) values ('Alan', 'Nunez', '11-18-2003');
insert into gymnasts (first_name, last_name, birthdate) values ('Porfirio', 'carter', '11-18-2003');
insert into gymnasts (first_name, last_name, birthdate) values ('Aubrey', 'holden', '11-18-2003');
insert into gymnasts (first_name, last_name, birthdate) values ('carey', 'turner', '11-18-2003');
insert into gymnasts (first_name, last_name, birthdate) values ('adolfo', 'barber', '11-18-2003');

insert into classes (name) values ('class 1');
insert into classes (name) values ('class 2');
insert into classes (name) values ('class 3');
insert into classes (name) values ('class 4');

insert into coaches (first_name, last_name) values ('Greg', 'Skupski');
insert into coaches (first_name, last_name) values ('Tim', 'Herring');

insert into gymnast_classes(gymnast_id, class_id) values(1,1);
insert into gymnast_classes(gymnast_id, class_id) values(2,1);
insert into gymnast_classes(gymnast_id, class_id) values(3,1);

insert into class_coaches(coach_id, class_id) values (1,1);
insert into class_coaches(coach_id, class_id) values (1,2);
insert into class_coaches(coach_id, class_id) values (1,3);
insert into class_coaches(coach_id, class_id) values (1,4);
