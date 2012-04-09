/*ignore tables or sequence doesn't exist Exception*/

begin execute immediate 'DROP TABLE users CASCADE CONSTRAINTS PURGE';
exception when others then if sqlcode = -00942 then null; else raise; end if; end;
/

CREATE TABLE users
(
    login VARCHAR(200),
    password VARCHAR(200) not null,
    CONSTRAINT login_pk PRIMARY KEY(login)
);

INSERT INTO users (login, password) VALUES ('vlad', 'qwerty');
/
commit;

    