/*ignore tables or sequence doesn't exist Exception*/

begin execute immediate 'DROP TABLE users CASCADE CONSTRAINTS PURGE';
exception when others then if sqlcode = -00942 then null; else raise; end if; end;
/

CREATE TABLE users
(
    user_id NUMBER(10),
    login VARCHAR(200),
    password VARCHAR(200) not null,
    CONSTRAINT user_id PRIMARY KEY(user_id)
);
CREATE SEQUENCE id_generator;

INSERT INTO users (user_id,login, password) VALUES (id_generator.nextval,'vlad', 'qwqerty');
/


SELECT * FROM users;

commit;

rollback;

select count(1) from users;

select * from users;

    