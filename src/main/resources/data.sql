insert into mobility_status (id, name, description, created, version) values (1,'Requested', 'Student se prijavio za mobilnost', now(), 1);
insert into mobility_status (id, name, description, created, version) values (2,'Created', 'Student je predao sve potrebne dokumente i njegov zahtjev je odobren', now(), 1);
insert into mobility_status (id, name, description, created, version) values (3,'Rejected', 'Studentov zahtjev nije ispravno izvršen te je isti odbijen', now(), 1);
insert into mobility_status (id, name, description, created, version) values (4,'Rejected by host', 'Studentov zahtjev odbijen je od strane prihvatne institucije', now(), 1);
insert into mobility_status (id, name, description, created, version) values (5,'Acttive', 'Student se nalazi na mobilnosti', now(), 1);
insert into mobility_status (id, name, description, created, version) values (6,'Cancelled', 'Mobilnost prekinuta prije predviđenog završetka', now(), 1);
insert into mobility_status (id, name, description, created, version) values (7,'Done', 'Mobilnost je završena', now(), 1);

insert into approval_type (id, name, description, created, version) values (1,'Apply approved', 'Prijava za mobilnost odobrena', now(), 1);
insert into approval_type (id, name, description, created, version) values (2,'Apply rejected', 'Prijava za mobilnost odbijena', now(), 1);
insert into approval_type (id, name, description, created, version) values (3,'Subjects approved', 'Predmeti koje student želi upisati na mobilnosti su prihvaćeni', now(), 1);
insert into approval_type (id, name, description, created, version) values (4,'Subjects rejected', 'Predmeti koje student želi upisati na mobilnosti su prihvaćeni', now(), 1);
insert into approval_type (id, name, description, created, version) values (5,'Documentation before delivered successfully', 'Sva porebna dokumentacija prije mobilnosti je dostavljena', now(), 1);
insert into approval_type (id, name, description, created, version) values (6,'Documentation before delivered unsuccessfully', 'Sva porebna dokumentacija prije mobilnosti nije uredno dostavljena', now(), 1);
insert into approval_type (id, name, description, created, version) values (7,'Documentation after delivered successfully', 'Sva porebna dokumentacija nakon mobilnosti je dostavljena', now(), 1);
insert into approval_type (id, name, description, created, version) values (8,'Documentation after delivered unsuccessfully', 'Sva porebna dokumentacija nakon mobilnosti nije uredno dostavljena', now(), 1);

insert into role (id, role, created, version) values (1,'ROLE_ADMIN', now(), 1);
insert into role (id, role, created, version) values (2,'ROLE_COORDINATOR', now(), 1);
insert into role (id, role, created, version) values (3,'ROLE_SUBJECT_COORDINATOR', now(), 1);
insert into role (id, role, created, version) values (4,'ROLE_ERASMUS_STUDENT', now(), 1);
insert into role (id, role, created, version) values (5,'ROLE_VISITOR', now(), 1);
