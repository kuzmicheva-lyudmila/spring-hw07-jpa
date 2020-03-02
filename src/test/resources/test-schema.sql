create table authors (
    id identity Primary Key,
    full_name varchar(240) not null,
    description varchar(2000)
);

create table book_genres (
    id identity Primary Key,
    genre varchar(240) unique not null
);

create table books (
    id identity primary key,
    full_name varchar(240) not null,
    genre_id integer references book_genres(id),
    book_description varchar(2000)
);

create table book_authors (
    book_id integer not null references books(id) on delete cascade,
    author_id integer not null references authors(id),
    primary key (book_id, author_id)
);

create table posts (
    id identity primary key,
    book_id bigint not null references books(id),
    description varchar(2000)
);
