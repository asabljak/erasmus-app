insert into mobility_status (id, name, description, created, version) values (1,'Requested', 'Student se prijavio za mobilnost', now(), 1);
insert into mobility_status (id, name, description, created, version) values (2,'Created', 'Student je predao sve potrebne dokumente i njegov zahtjev je odobren', now(), 1);
insert into mobility_status (id, name, description, created, version) values (3,'Rejected', 'Studentov zahtjev nije ispravno izvršen te je isti odbijen', now(), 1);
insert into mobility_status (id, name, description, created, version) values (4,'Rejected by host', 'Studentov zahtjev odbijen je od strane prihvatne institucije', now(), 1);
insert into mobility_status (id, name, description, created, version) values (5,'Active', 'Student se nalazi na mobilnosti', now(), 1);
insert into mobility_status (id, name, description, created, version) values (6,'Cancelled', 'Mobilnost prekinuta prije predviđenog završetka', now(), 1);
insert into mobility_status (id, name, description, created, version) values (7,'Done', 'Mobilnost je završena, ali je još potrebno ispuniti obveze nakon mobilnosti', now(), 1);
insert into mobility_status (id, name, description, created, version) values (8,'All done', 'Sve obveze su ispunjene', now(), 1);

insert into approval_type (id, name, description, created, version) values (1,'Applied', 'Korisnik se prijavio za mobilnost', now(), 1);
insert into approval_type (id, name, description, created, version) values (2,'Subjects', 'Predmeti koje student želi upisati na mobilnosti', now(), 1);
insert into approval_type (id, name, description, created, version) values (3,'Documentation before delivered', 'Sva potrebna dokumentacija prije mobilnosti je dostavljena', now(), 1);
insert into approval_type (id, name, description, created, version) values (4,'Documentation after delivered', 'Sva porebna dokumentacija nakon mobilnosti je dostavljena', now(), 1);
insert into approval_type (id, name, description, created, version) values (5,'Grant', 'Korisnik je dobio odobrenje za odlazak na mobilnost od strane domaće institucije', now(), 1);
insert into approval_type (id, name, description, created, version) values (6,'Scholarship before', 'Odobren prvi dio stipendije koju korisnik dobiva prije mobilnosti', now(), 1);
insert into approval_type (id, name, description, created, version) values (7,'Scholarship after', 'Odobren drugi dio stipendije koju korisnik dobiva nakon mobilnosti', now(), 1);
insert into approval_type (id, name, description, created, version) values (8,'Social scholarship', 'Odobrena dodatna stipendija za studente slabijeg socioekonomskog statusa', now(), 1);

insert into semester_type(id, created, version, code, name) values (1, now(), 1, 'SUMMER', 'Ljetni');
insert into semester_type(id, created, version, code, name) values (2, now(), 1, 'WINTER', 'Zimski');

insert into role (id, role, created, version) values (1,'ROLE_ADMIN', now(), 1);
insert into role (id, role, created, version) values (2,'ROLE_COORDINATOR', now(), 1);
insert into role (id, role, created, version) values (3,'ROLE_SUBJECT_COORDINATOR', now(), 1);
insert into role (id, role, created, version) values (4,'ROLE_ERASMUS_STUDENT', now(), 1);
insert into role (id, role, created, version) values (5,'ROLE_VISITOR', now(), 1);

insert into institution(id, created, version, city, name, code, country, web_url) values (-1, now(), 1, 'Zagreb', 'Tehničko veleučilište u Zagrebu', 'TVZ', 'Croatia', 'http://www.tvz.hr');

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

insert into notification_type (id, name, description, created, version) values (0,'Apply', 'Korisnik se prijavio za mobilnost', now(), 1);
insert into notification_type (id, name, description, created, version) values (1,'Approval', 'Korisnik je dobio novi approval', now(), 1);
insert into notification_type (id, name, description, created, version) values (2,'Interview', 'Korisnik je pozvan na intervju', now(), 1);
insert into notification_type (id, name, description, created, version) values (3,'Review', 'Korisnik je poslan upit da recenzira ustanovu na kojoj je izvršavao mobilnost', now(), 1);
insert into notification_type (id, name, description, created, version) values (4,'Subjects Approval', 'Korisnik je prijavio predmete koje želi upisati na mobilnosti', now(), 1);
insert into notification_type (id, name, description, created, version) values (5,'Response', 'Odgovor na zahtjev', now(), 1);
