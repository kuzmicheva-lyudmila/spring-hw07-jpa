insert into authors(full_name, description)
values ('author1', 'descirption'),
       ('author2', 'descirption');

insert into book_genres(genre)
values ('romance'), ('history'), ('horror'), ('children''s books');

insert into books(full_name, genre_id, book_description)
values ('the book1', 2, 'description'),
       ('the book2', 2, 'description');
--
--insert into book_authors(book_id, author_id)
--values (1, 1), (1, 2), (2, 1);

insert into posts (book_id, description)
values (1, 'description1'), (1, 'description2'), (1, 'description3');
