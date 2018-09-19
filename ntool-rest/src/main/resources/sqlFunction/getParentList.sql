DROP FUNCTION IF EXISTS getFullName;
CREATE FUNCTION getFullName (rootId varchar(1000))
RETURNS varchar(1000)
BEGIN
DECLARE fid varchar(1000) default '';
DECLARE groupName varchar(1000) default '';
DECLARE fullName varchar(1000) default '';
WHILE rootId is not null  do
    SET fid =(SELECT f_group_id FROM group_info WHERE group_id = rootId);
    SET groupName=(SELECT group_name FROM group_info WHERE group_id = rootId);
    IF fid is not null THEN
		SET fullName = concat(groupName, '_', fullName);
        SET rootId = fid;
    ELSE
        SET rootId = fid;
    END IF;
END WHILE;
SET fullName= TRIM('_' FROM fullName);
return fullName;
END;