CREATE TABLE IF NOT EXISTS library.acl_sid (
    id bigserial Primary Key,
    principal numeric(1) NOT NULL,
    sid varchar(100) NOT NULL,
    unique (sid, principal)
);

CREATE TABLE IF NOT EXISTS library.acl_class (
    id bigserial Primary Key,
    class varchar(255) NOT NULL,
    unique (class)
);

CREATE TABLE IF NOT EXISTS library.acl_entry (
    id bigserial Primary Key,
    acl_object_identity bigint NOT NULL,
    ace_order numeric(11) NOT NULL,
    sid bigint NOT NULL,
    mask numeric(11) NOT NULL,
    granting numeric(1) NOT NULL,
    audit_success numeric(1) NOT NULL,
    audit_failure numeric(1) NOT NULL,
    unique (acl_object_identity, ace_order)
);

CREATE TABLE IF NOT EXISTS library.acl_object_identity (
    id bigserial Primary Key,
    object_id_class bigint NOT NULL,
    object_id_identity varchar(36) NOT NULL,
    parent_object bigint DEFAULT NULL,
    owner_sid bigint DEFAULT NULL,
    entries_inheriting numeric(1) NOT NULL,
    unique (object_id_class, object_id_identity)
);

ALTER TABLE library.acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES library.acl_object_identity(id);

ALTER TABLE library.acl_entry
    ADD FOREIGN KEY (sid) REFERENCES library.acl_sid(id);

ALTER TABLE library.acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES library.acl_object_identity(id);

ALTER TABLE library.acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES library.acl_class(id);

ALTER TABLE library.acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES library.acl_sid(id);

INSERT INTO library.acl_sid(id, principal, sid) VALUES
(1, 0, 'ROLE_EDITOR'),
(2, 0, 'ROLE_EDITOR_HISTORY');

INSERT INTO library.acl_class(id, class) VALUES
(1, 'ru.otus.homework.model.Genre');

INSERT INTO library.acl_object_identity(id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 1, 0),
(2, 1, 2, NULL, 1, 0),
(3, 1, 3, NULL, 1, 0),
(4, 1, 4, NULL, 1, 0);

INSERT INTO library.acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 2, 1, 1, 1),
(2, 1, 1, 2, 1, 1, 1),
(2, 2, 2, 2, 1, 1, 1),
(3, 1, 1, 2, 1, 1, 1),
(4, 1, 1, 2, 1, 1, 1);

ALTER TABLE library.users add column roles text;

delete from library.users;
insert into library.users(user_name, password, roles) values
('user', '$2a$10$z4QtY3jNduuaWpMGw.EX0.EP5meB5oH0B1cCs0AGbzx6S7y5V00gK', 'ROLE_VIEWER'),
('editor_history', '$2a$10$z4QtY3jNduuaWpMGw.EX0.EP5meB5oH0B1cCs0AGbzx6S7y5V00gK', 'ROLE_VIEWER,ROLE_EDITOR_HISTORY'),
('editor', '$2a$10$z4QtY3jNduuaWpMGw.EX0.EP5meB5oH0B1cCs0AGbzx6S7y5V00gK', 'ROLE_VIEWER,ROLE_EDITOR')
on conflict do nothing;
-- password: 1234
