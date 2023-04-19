insert into APPLICATION (application_id, title, image, platform_type, created_date, modified_date)
values (9000001, 'mysql', 'mysql:latest', 'DOCKER', now()-1, now()-1)
     , (9000002, 'postgresql', 'postgresql:latest', 'DOCKER', now(), now())
     , (9000003, 'gantry', 'ghcr.io/gantry-project/gantry-docker:release', 'DOCKER', now(), now()) ;

insert into CONTAINER (container_id, application_id, created_date, modified_date)
values ('cxxxxx001', 9000001, now(), now())
     , ('cxxxxx022', 9000002, now(), now());

insert into users (id, email, enabled, authority, username, password)
values (9999999999, 'admin@admin.com', true, 'ADMIN', 'admin', 'admin')
     , (9999999998, 'gantry@admin.com', true, 'ADMIN', 'gantry', '$2a$10$27su2oYwVjfqhMax7Tn.MuKEgPyKEfbjjkD8/ovGohGwl21PVlX3O')
     , (8888888888, 'user@user.com', true, 'USER', 'user', '$2a$10$eumZRPGbixwv5JZQM6nsWuoekrXgXR43IQ4tU8COzSQ9phevy36CG');
