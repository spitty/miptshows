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
begin execute immediate 'DROP TABLE temp_data CASCADE CONSTRAINTS PURGE';
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
    file_name VARCHAR2(200) NOT NULL UNIQUE,
    file_size NUMBER(15) NOT NULL,
    CONSTRAINT files_pk PRIMARY KEY(file_id)  
);


CREATE TABLE folders (
    folder_id NUMBER(15),
    folder_name VARCHAR2(200) NOT NULL UNIQUE,
    CONSTRAINT folder_pk PRIMARY KEY(folder_id)  
);

CREATE TABLE files2folders 
(
    file_id NUMBER(15) NOT NULL,
    folder_id NUMBER(15) NOT NULL,
    CONSTRAINT file_fk FOREIGN KEY(file_id) REFERENCES files(file_id),
    CONSTRAINT folder_fk FOREIGN KEY(folder_id) REFERENCES folders(folder_id),
    CONSTRAINT files2folder_pk PRIMARY KEY(file_id, folder_id)  
);


CREATE TABLE servers 
(
    server_id NUMBER(15),
    server_name VARCHAR2(200) NOT NULL UNIQUE,
    CONSTRAINT server_pk PRIMARY KEY(server_id)  
);

CREATE SEQUENCE file_id_generator;

CREATE SEQUENCE folder_id_generator;

CREATE SEQUENCE server_id_generator;

CREATE TABLE temp_data
(
    file_name VARCHAR2(200),
    folder_name VARCHAR2(200) NOT NULL,
    server_name VARCHAR2(200) NOT NULL,
    file_size NUMBER(15) NOT NULL,
    CONSTRAINT temp_data_pk PRIMARY KEY (file_name)
);


INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file1', 'folder2', 13453453453432, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file2', 'folder1', 200, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file3', 'folder4', 133300, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file4', 'folder1', 200, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file5', 'folder1', 100, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file6', 'folder2', 1230, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file7', 'folder1', 100, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file8', 'folder3', 200, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file9', 'folder1', 1456, 'natalie');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file10', 'folder777', 3453, 'dgap');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file11', 'folder222', 100, 'dgap');

INSERT INTO temp_data (file_name, folder_name, file_size, server_name) VALUES ('file12', 'folder222', 200, 'dgap');


commit;


/*DELETE FROM temp_data;

DELETE FROM temp_data WHERE file_name = 'vasya';

SELECT * FROM temp_data;

commit;
*/
/*
select * from temp_data;


MERGE
    INTO files f
    USING temp_data td
    ON (td.file_name = f.file_name)
    WHEN NOT MATCHED THEN
        INSERT
            (f.file_id, f.file_name, f.file_size)
        VALUES
            (file_id_generator.nextval, td.file_name, td.file_size)
;

MERGE
    INTO folders f
    USING (
            SELECT UNIQUE folder_name 
            FROM temp_data
          ) td
    ON (td.folder_name = f.folder_name)
    WHEN NOT MATCHED THEN
        INSERT
            (f.folder_id, f.folder_name)
        VALUES
            (folder_id_generator.nextval, td.folder_name)
;            


MERGE
    INTO files2folders f2f
    USING
    (
        SELECT file_id, folder_id
        FROM files f, folders fol, temp_data td
        WHERE f.file_name = td.file_name
            AND fol.folder_name = td.folder_name
    ) t
    ON ( t.file_id = f2f.file_id AND t.folder_id = f2f.folder_id )
    WHEN NOT MATCHED THEN
        INSERT
            (f2f.file_id, f2f.folder_id)
        VALUES
            (t.file_id, t.folder_id)
;            

MERGE
    INTO servers s
    USING (SELECT UNIQUE server FROM temp_data) td
    ON (td.server = s.server)
    WHEN NOT MATCHED THEN
        INSERT
            (s.server_id, s.server)
        VALUES
            (server_id_generator.nextval, td.server)
;
            
select * from files;

select * from folders;

select * from files2folders;

select * from servers;

SELECT file_name 
FROM files f JOIN files2folders ftf
ON F.FILE_ID = ftf.file_id
WHERE ftf.folder_id = 10;
*/