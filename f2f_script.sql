/*ignore tables or sequence doesn't exist Exception*/

begin execute immediate 'DROP TABLE files CASCADE CONSTRAINTS PURGE';
exception when others then if sqlcode = -00942 then null; else raise; end if; end;
/
begin execute immediate 'DROP TABLE folders CASCADE CONSTRAINTS PURGE';
exception when others then if sqlcode = -00942 then null; else raise; end if; end;
/
begin execute immediate 'DROP TABLE files2folders CASCADE CONSTRAINTS PURGE';
exception when others then if sqlcode = -00942 then null; else raise; end if; end;
/
begin execute immediate 'DROP TABLE servers CASCADE CONSTRAINTS PURGE';
exception when others then if sqlcode = -00942 then null; else raise; end if; end;
/
begin execute immediate 'DROP TABLE tempdata CASCADE CONSTRAINTS PURGE';
exception when others then if sqlcode = -00942 then null; else raise; end if; end;
/
begin execute immediate 'DROP SEQUENCE file_id_generator';
exception when others then if sqlcode = -02289 then null; else raise; end if; end;
/
begin execute immediate 'DROP SEQUENCE folder_id_generator';
exception when others then if sqlcode = -02289 then null; else raise; end if; end;
/
begin execute immediate 'DROP SEQUENCE server_id_generator';
exception when others then if sqlcode = -02289 then null; else raise; end if; end;
/
/* end of ignoring procedure */


CREATE TABLE files 
(
    file_id NUMBER(15),
    file_name VARCHAR2(200) not null,
    file_size NUMBER(15),
    CONSTRAINT files_pk PRIMARY KEY(file_id)  
);


CREATE TABLE folders (
    folder_id NUMBER(15),
    folder_name VARCHAR2(200) not null,
    CONSTRAINT folder_pk PRIMARY KEY(folder_id)  
);

CREATE TABLE files2folders 
(
    file_id NUMBER(15) not null,
    folder_id NUMBER(15) not null,
    CONSTRAINT file_fk FOREIGN KEY(file_id) REFERENCES files(file_id),
    CONSTRAINT folder_fk FOREIGN KEY(folder_id) REFERENCES folders(folder_id),
    CONSTRAINT files2folder_pk PRIMARY KEY(file_id, folder_id)  
);


CREATE TABLE servers 
(
    server_id NUMBER(15),
    server VARCHAR2(200) not null,
    CONSTRAINT server_pk PRIMARY KEY(server_id)  
);

CREATE SEQUENCE file_id_generator;

CREATE SEQUENCE folder_id_generator;

CREATE SEQUENCE server_id_generator;

CREATE TABLE temp_data
(
    file_name VARCHAR2(200),
    folder_name VARCHAR2(200) not null,
    server VARCHAR2(200) not null,
    file_size NUMBER(15) not null,
    CONSTRAINT temp_data_pk PRIMARY KEY (file_name)
);


/*INSERT INTO temp_data (file_name, folder_name, file_size, server) 
VALUES ('dls', 'roma', 100, 'vodka');

DELETE FROM temp_data;

DELETE FROM temp_data WHERE file_name = 'vasya';

SELECT * FROM temp_data;

commit;
*/