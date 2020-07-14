create table if not exists library.users (
  id bigserial primary key,
  user_name varchar(45) not null,
  password varchar(60) not null,
  active boolean not null default true
);

-- password: 1234
insert into library.users(user_name, password)
values ('user', '$2a$10$z4QtY3jNduuaWpMGw.EX0.EP5meB5oH0B1cCs0AGbzx6S7y5V00gK')
on conflict do nothing;
