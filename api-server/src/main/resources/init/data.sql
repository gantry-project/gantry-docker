insert into APPLICATION (application_id, title, image, platform_type, created_date, modified_date)
values (1, 'mysql', 'mysql/mysql:latest', 'DOCKER', now()-1, now()-1)
     , (2, 'postgresql', 'postgresql/postgresql:latest', 'DOCKER', now(), now()) ;

insert into CONTAINER (container_id, application_id, status, created_date, modified_date)
values ('c001', 1, 'RUNNING', now(), now())
     , ('c022', 2, 'RUNNING', now(), now());

insert into users (id, email, enabled, password, username, authority)
values (9999999999, 'admin@admin.com', true, 'admin', 'admin', 'ADMIN')
     , (8888888888, 'user@user.com', true, '$2a$10$eumZRPGbixwv5JZQM6nsWuoekrXgXR43IQ4tU8COzSQ9phevy36CG', 'user', 'USER');
