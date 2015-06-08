/*
Ricky Chen 860998560
Steven Em 860878417
GroupId 19
*/
COPY USR(userId,password,email,name,dateofBirth) 
FROM '/tmp/rchen016/CS166_Project/data/USR.csv' 
WITH DELIMITER ',' CSV HEADER;

COPY WORK_EXPR(userId,company,role,location,startDate,endDate)
FROM '/tmp/rchen016/CS166_Project/data/Work_Ex.csv' 
WITH DELIMITER ',' CSV HEADER;

COPY EDUCATIONAL_DETAILS(userId,instituitionName,major,degree,startdate,enddate) 
FROM '/tmp/rchen016/CS166_Project/data/Edu_Det.csv' 
WITH DELIMITER ',' CSV HEADER;

COPY MESSAGE(msgId,senderId,receiverId,contents,sendTime,deleteStatus,status) 
FROM '/tmp/rchen016/CS166_Project/data/Message.csv' 
WITH DELIMITER ',' CSV HEADER;

COPY CONNECTION_USR(userId,connectionId,status) 
FROM '/tmp/rchen016/CS166_Project/data/Connection.csv' 
WITH DELIMITER ',' CSV HEADER;

