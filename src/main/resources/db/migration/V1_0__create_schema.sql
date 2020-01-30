create table roles
(
  id serial not null,
  role varchar(20) not null unique default '',
  primary key (id)
);

create table users
(
  id serial not null,
  email varchar(50) not null unique,
  password varchar(150),
  primary key (id)
);
create index on users(email);

create table user_role
(
  user_id serial not null,
  role_id smallserial not null,
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);