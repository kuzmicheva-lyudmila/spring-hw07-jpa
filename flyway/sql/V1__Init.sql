CREATE SCHEMA IF NOT EXISTS library;

create table IF NOT EXISTS library.authors (
    id bigserial Primary Key,
    full_name varchar(240) not null,
    description varchar(2000)
);

create table IF NOT EXISTS library.book_genres (
    id bigserial primary key,
    genre varchar(240)
);

create table IF NOT EXISTS library.books (
    id bigserial primary key,
    full_name varchar(240) not null,
    genre_id bigint references library.book_genres(id),
    book_description varchar(2000)
);

create table IF NOT EXISTS library.book_authors (
    book_id bigint not null references library.books(id) on delete cascade,
    author_id bigint not null references library.authors(id),
    primary key (book_id, author_id)
);

create table if not exists library.posts (
    id bigserial primary key,
    book_id bigint not null references library.books(id) on delete cascade,
    description varchar(2000)
);

insert into library.book_genres (genre) values
    ('romance'),
    ('history'),
    ('horror'),
    ('children''s books')
ON CONFLICT DO NOTHING;
