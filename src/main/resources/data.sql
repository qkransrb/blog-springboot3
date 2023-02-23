insert into roles (`name`) values ('ROLE_ADMIN');
insert into roles (`name`) values ('ROLE_USER');

insert into users (`email`, `name`, `password`, `username`) values ('admin@example.com', 'admin', '$2a$12$xKIVkm1TblGaSjT8pKVvxOS5CIOw.7NGuyfuQYPqc7qmZbTj6Kgda', 'admin');
insert into users (`email`, `name`, `password`, `username`) values ('user@example.com', 'admin', '$2a$12$xKIVkm1TblGaSjT8pKVvxOS5CIOw.7NGuyfuQYPqc7qmZbTj6Kgda', 'user');

insert into users_roles (`user_id`, `role_id`) values (1, 1);
insert into users_roles (`user_id`, `role_id`) values (2, 2);

insert into categories (`name`, `description`) values ('Java', 'Java Category');
insert into categories (`name`, `description`) values ('NodeJS', 'NodeJS Category');
insert into categories (`name`, `description`) values ('Python', 'Python Category');