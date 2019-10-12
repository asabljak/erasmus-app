insert into mobility_status (id, name, description, created, version) values (1,'Requested', 'Student se prijavio za mobilnost', now(), 1);
insert into mobility_status (id, name, description, created, version) values (2,'Created', 'Student je predao sve potrebne dokumente i njegov zahtjev je odobren', now(), 1);
insert into mobility_status (id, name, description, created, version) values (3,'Rejected', 'Studentov zahtjev nije ispravno izvršen te je isti odbijen', now(), 1);
insert into mobility_status (id, name, description, created, version) values (4,'Rejected by host', 'Studentov zahtjev odbijen je od strane prihvatne institucije', now(), 1);
insert into mobility_status (id, name, description, created, version) values (5,'Active', 'Student se nalazi na mobilnosti', now(), 1);
insert into mobility_status (id, name, description, created, version) values (6,'Cancelled', 'Mobilnost prekinuta prije predviđenog završetka', now(), 1);
insert into mobility_status (id, name, description, created, version) values (7,'Done', 'Mobilnost je završena', now(), 1);

insert into approval_type (id, name, description, created, version) values (0,'Appllied', 'Student se prijavio za mobilnost', now(), 1);
insert into approval_type (id, name, description, created, version) values (1,'Apply approved', 'Prijava za mobilnost odobrena', now(), 1);
insert into approval_type (id, name, description, created, version) values (2,'Apply rejected', 'Prijava za mobilnost odbijena', now(), 1);
insert into approval_type (id, name, description, created, version) values (3,'Subjects approved', 'Predmeti koje student želi upisati na mobilnosti su prihvaćeni', now(), 1);
insert into approval_type (id, name, description, created, version) values (4,'Subjects rejected', 'Predmeti koje student želi upisati na mobilnosti nisu prihvaćeni', now(), 1);
insert into approval_type (id, name, description, created, version) values (5,'Documentation before delivered successfully', 'Sva potrebna dokumentacija prije mobilnosti je dostavljena', now(), 1);
-- insert into approval_type (id, name, description, created, version) values (6,'Documentation before delivered unsuccessfully', 'Sva potrebna dokumentacija prije mobilnosti nije uredno dostavljena', now(), 1);
insert into approval_type (id, name, description, created, version) values (7,'Documentation after delivered successfully', 'Sva porebna dokumentacija nakon mobilnosti je dostavljena', now(), 1);
-- insert into approval_type (id, name, description, created, version) values (8,'Documentation after delivered unsuccessfully', 'Sva porebna dokumentacija nakon mobilnosti nije uredno dostavljena', now(), 1);
insert into approval_type (id, name, description, created, version) values (9,'Grant', 'Student je dobio odobrenje za odlazak na mobilnost od strane domaće institucije', now(), 1);
insert into approval_type (id, name, description, created, version) values (10,'Scholarship before', 'Odobren prvi dio stipendije koju student dobiva prije mobilnosti', now(), 1);
insert into approval_type (id, name, description, created, version) values (11,'Scholarship after', 'Odobren drugi dio stipendije koju student dobiva nakon mobilnosti', now(), 1);
insert into approval_type (id, name, description, created, version) values (12,'Social scholarship', 'Odobrena dodatna stipendija za studente slabijeg socioekonomskog statusa', now(), 1);

insert into semester_type(id, created, version, code, name) values (1, now(), 1, 'SUMMER', 'Ljetni');
insert into semester_type(id, created, version, code, name) values (2, now(), 1, 'WINTER', 'Zimski');

insert into role (id, role, created, version) values (1,'ROLE_ADMIN', now(), 1);
insert into role (id, role, created, version) values (2,'ROLE_COORDINATOR', now(), 1);
insert into role (id, role, created, version) values (3,'ROLE_SUBJECT_COORDINATOR', now(), 1);
insert into role (id, role, created, version) values (4,'ROLE_ERASMUS_STUDENT', now(), 1);
insert into role (id, role, created, version) values (5,'ROLE_VISITOR', now(), 1);

insert into institution(	id, created, version, city, name, code, country, web_url) values (-1, now(), 1, 'Zagreb', 'Tehničko veleučilište u Zagrebu', 'TVZ', 'Croatia', 'http://www.tvz.hr');

insert into field(id, code, name, created, version)	values (1, 'RAČ', 'Računarstvo', now(), 1);
insert into field(id, code, name, created, version)	values (2, 'INFO', 'Informatika', now(), 1);
insert into field(id, code, name, created, version)	values (3, 'ELO', 'Elektrotehnika', now(), 1);
insert into field(id, code, name, created, version)	values (4, 'STRO', 'Strojarstvo', now(), 1);
insert into field(id, code, name, created, version)	values (5, 'MEH', 'Mehatronika', now(), 1);

insert into document_type(id, created, version, code, description, name) values (1, now(), 1, 'MOTIVACIJSKO_PISMO', 'Motivacijsko pismo na hrvatskom i  engleskom jeziku, ili jeziku ustanove na kojoj se vrši mobilnost', 'Motivacijsko pismo');
insert into document_type(id, created, version, code, description, name) values (2, now(), 1, 'PRIJAVA', 'Prijavni obrazac opis', 'Prijavni obrazac za Erasmus mobilnost');
insert into document_type(id, created, version, code, description, name) values (3, now(), 1, 'DOMOVNICA', 'Domovnica studenta koji se prijavljuje za mobilnost', 'Domovnica');
insert into document_type(id, created, version, code, description, name) values (4, now(), 1, 'CV', 'CV u Europass formi', 'Životopis');
insert into document_type(id, created, version, code, description, name) values (5, now(), 1, 'STATUS_STUDENTA', 'Potvrda o statusu studenta', 'Status studenta');
insert into document_type(id, created, version, code, description, name) values (6, now(), 1, 'PRIJEPIS_OCJENA', 'Prijepis ocjena svih položenih ispita', 'Prijepis ocjena');
insert into document_type(id, created, version, code, description, name) values (7, now(), 1, 'LA', 'LA opis', 'Learning Agreement obrazac');
insert into document_type(id, created, version, code, description, name) values (8, now(), 1, 'TOR', 'Transcript Of Records obrazac', 'Transcript Of Records');
insert into document_type(id, created, version, code, description, name) values (9, now(), 1, 'OSOBNA', 'Preslika osobne iskaznice studenta', 'Preslika osobne');
insert into document_type(id, created, version, code, description, name) values (10, now(), 1, 'ZIRO', 'Preslika žiro računa studenta', 'Preslika žiro računa');
insert into document_type(id, created, version, code, description, name) values (11, now(), 1, 'RACUN', 'Račun usluge za koju student očekuje povrat novca od Veleučilišta', 'Račun');
insert into document_type(id, created, version, code, description, name) values (12, now(), 1, 'SLIKA', 'Fotografija koju student predaje nakon mobilnosti', 'Fotografija');
insert into document_type(id, created, version, code, description, name) values (13, now(), 1, 'OSTALO', 'Ostali dokumenti koji nisu navedeni kao zaseban tip dokumenta', 'Ostalo');