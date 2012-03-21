
CREATE TABLE files 
(
    file_id NUMBER(15) not null,
    filename VARCHAR(200) not null,
    CONSTRAINT files_pk PRIMARY KEY(file_id)  
);


CREATE TABLE folders (
    folder_id NUMBER(15) not null,
    folder VARCHAR(200) not null,
    CONSTRAINT folder_pk PRIMARY KEY(folder_id)  
);

CREATE TABLE servers 
(
    server_id NUMBER(15) not null,
    server VARCHAR(200) not null,
    CONSTRAINT server_pk PRIMARY KEY(server_id)  
);

CREATE TABLE file2folders 
(
    file_id NUMBER(15) not null,
    folder_id NUMBER(15) not null,
    CONSTRAINT file_fk FOREIGN KEY(file_id) REFERENCES files(file_id),
    CONSTRAINT folder_fk FOREIGN KEY(folder_id) REFERENCES folders(folder_id),
    CONSTRAINT file2folder_pk PRIMARY KEY(file_id, folder_id)  
);
