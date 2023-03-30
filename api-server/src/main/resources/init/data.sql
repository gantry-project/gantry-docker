insert into APPLICATION (application_id, title, image, platform_type, created_date, modified_date)
values (1, 'mysql', 'mysql/mysql:latest', 'DOCKER', now()-1, now()-1)
     , (2, 'postgresql', 'postgresql/postgresql:latest', 'DOCKER', now(), now()) ;
insert into CONTAINER (container_id, application_id, status, created_date, modified_date)
    values ('c001', 1, 'RUNNING', now(), now())
         , ('c022', 2, 'RUNNING', now(), now());
