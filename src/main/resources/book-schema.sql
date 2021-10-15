drop table if exists book CASCADE;
create table book
 (
 	id integer primary key auto_increment,
 	title varchar(255),
 	author varchar(255),
 	pages integer,
 	genre varchar(255)
 );