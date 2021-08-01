DROP TABLE IF EXISTS filedetails;


CREATE TABLE filedetails (
   id        VARCHAR(100)  NOT NULL,
  fileName        VARCHAR(100) PRIMARY KEY NOT NULL,
  unzip                   TEXT,
  filesize           TEXT,
  transfer          TEXT ,
  noOfRecords          TEXT ,
  persist  TEXT);

