create table item
(
 	id integer not null,
 	name varchar(255) not null,
 	category varchar(255) not null,
 	description varchar(255),
 	seller varchar(255) not null,
 	isdeleted boolean not null,
 	primary key(id)
);


create table users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);

 insert into users(username, password, enabled)values('default','password',true);
 insert into authorities(username,authority)values('default','ROLE_SELLER');
 
 insert into users(username, password, enabled)values('user','password',true);
 insert into authorities(username,authority)values('user','ROLE_USER');
 
 insert into item(id, name, category, description, seller, isdeleted )values('1','titan 2000 watch xp','watches', 'nothing', 'arao', false);
 
 insert into item(id, name, category, description, seller, isdeleted )values('2','shirt 42cm men','clothing', 'nothing', 'none', false);