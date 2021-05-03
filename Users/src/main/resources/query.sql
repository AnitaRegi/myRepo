drop table if exists role;
drop table if exists user;
drop table if exists user_roles;
create table role (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=MyISAM;
create table user (id bigint not null auto_increment, username varchar(255), password varchar(255), email varchar(255), phone varchar(255), name varchar(255));
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=MyISAM;