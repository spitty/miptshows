
MERGE
    INTO files f
    USING temp_data td
    ON (td.file_name = f.file_name)
    WHEN NOT MATCHED THEN
        INSERT
            (f.file_id, f.file_name, f.file_size, f.last_change_date)
        VALUES
            (file_id_generator.nextval, td.file_name, td.file_size, sysdate)
    WHEN MATCHED THEN
        UPDATE SET f.last_change_date = sysdate
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
    USING (SELECT UNIQUE server_name FROM temp_data) td
    ON (td.server_name = s.server_name)
    WHEN NOT MATCHED THEN
        INSERT
            (s.server_id, s.server_name)
        VALUES
            (server_id_generator.nextval, td.server_name)
;
  


SELECT count(*), folder_id
FROM files2folders
WHERE folder_id IN
    (
    SELECT folder_id 
    FROM files2folders
    WHERE file_id IN
        (
        SELECT file_id
        FROM files
        WHERE file_name = 'The Simpsons [s01e01].txt'
        OR file_name = 'House [s01e01].txt'
        )
    )
GROUP BY folder_id;

DELETE FROM files
WHERE sysdate - last_change_date > 0.001;

SELECT to_char(sysdate, 'DD')
FROM dual;

SELECT sysdate - last_change_date
FROM files;



DELETE FROM folders 
WHERE folder_id NOT IN
(
    SELECT folder_id
    FROM files2folders
)
;

SELECT folder_id FROM folders 
WHERE folder_id NOT IN
(
    SELECT folder_id
    FROM files2folders
)
;

INSERT INTO folders (folder_id, folder_name) VALUES (4,'smb://natalie.campus/incoming/! For/ForMiptShows/MiptShowsTest/1234/');

select * from temp_data;
          
select * from files;

select * from folders;

select * from files2folders;

select * from servers;

DELETE FROM temp_data;

DELETE FROM files;

DELETE FROM folders;

DELETE FROM files2folders;

DELETE FROM servers;

commit;

SELECT file_name 
FROM files f JOIN files2folders ftf
ON F.FILE_ID = ftf.file_id
WHERE ftf.folder_id = 10;



SELECT DISTINCT fol.folder_name, f.file_name
    FROM folders fol, files f, files2folders f2f
    WHERE f2f.file_id = f.file_id 
    AND f2f.folder_id = fol.folder_id
    AND f.file_name LIKE 
    ''||regexp_replace('House','[^a-zà-ÿ¸0-9]+','%')||'%s'||lpad(1, 2, '0')||'%e'||lpad(1, 2, '0')||'%'
    OR f.file_name LIKE 
    ''||regexp_replace('House','[^a-zà-ÿ¸0-9]+','%')||'%'||1||'x'||lpad(1, 2, '0')||'%'
    OR f.file_name LIKE 
    ''||regexp_replace('House','[^a-zà-ÿ¸0-9]+','%')||'%'||1||''||lpad(1, 2, '0')||'%'
 ;



SELECT file_name FROM files
WHERE file_name LIKE '%t%'
;       

SELECT ''||regexp_replace('House','[^a-zà-ÿ¸0-9]+','%')||'%s'||lpad(1, 2, '0')||'%e'||lpad(1, 2, '0')||'%' 
as searchstr FROM files;


;

select file_id
from files
where file_name like 'Tractor Man%'


/*
DELETE FROM folders 
WHERE folder_id IN
(
    SELECT  folder_id
    FROM files2folders
    WHERE folder_id IN
    (
        SELECT folder_id
         FROM folders
    )AND 
    count(folder_id) = 0
GROUP BY folder_id
);
*

/*
SELECT folder_id
FROM files2folders
WHERE folder_id IN
(
   SELECT folder_id
   FROM folders
)
GROUP BY folder_id
HAVING count(folder_id) != 0;
;
*/
