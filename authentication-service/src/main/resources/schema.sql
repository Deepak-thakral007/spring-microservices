DROP TABLE IF EXISTS oauth_client_details;

CREATE TABLE  oauth_client_details (
  client_id varchar(255) not null PRIMARY KEY,
  client_secret varchar(255) not null,
  web_server_redirect_uri varchar(2048) default null,
  scope varchar(255) default null,
  access_token_validity int  default null,
  refresh_token_validity int default null,
  resource_ids varchar(1024) default null,
  authorized_grant_types varchar(1024) default null,
  authorities varchar(1024) default null,
  additional_information varchar(4096) default null,
  autoapprove varchar(255) default null

);

DROP  TABLE  IF EXISTS permission CASCADE;
CREATE TABLE  permission (
  id SERIAL PRIMARY KEY,
  name varchar(512) default null UNIQUE
);

DROP TABLE IF EXISTS role CASCADE;
CREATE TABLE  role (
  id SERIAL PRIMARY KEY,
  name varchar(255) default null UNIQUE

)  ;
DROP TABLE IF EXISTS user1 CASCADE;
CREATE TABLE  user1 (
  id SERIAL PRIMARY KEY,
  username varchar(100) not null UNIQUE,
  password varchar(1024) not null,
  email varchar(1024) not null,
  enabled int not null,
  accountNonExpired int not null,
  credentialsNonExpired int not null,
  accountNonLocked int not null
)   ;




DROP TABLE IF EXISTS role_user;
CREATE TABLE role_user (
  role_id int default null,
  user_id int default null,
  constraint role_user_ibfk_1 foreign key (role_id) references role (id),
  constraint role_user_ibfk_2 foreign key (user_id) references user1 (id)
)   ;

DROP TABLE IF EXISTS permission_role;
CREATE TABLE  permission_role (
  permission_id int default null,
  role_id int default null,
  constraint permission_role_ibfk_1 foreign key (permission_id) references permission (id),
  constraint permission_role_ibfk_2 foreign key (role_id) references role (id)
)  ;


DROP TABLE IF EXISTS oauth_client_token;
-- token store
CREATE TABLE oauth_client_token (
  token_id VARCHAR(256),
  token  bytea,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);
DROP TABLE IF EXISTS oauth_access_token;
CREATE TABLE oauth_access_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication bytea,
  refresh_token VARCHAR(256)
);
DROP TABLE IF EXISTS oauth_refresh_token;
CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(256),
  token bytea,
  authentication  bytea
);
DROP TABLE IF EXISTS oauth_code;
CREATE TABLE oauth_code (
  code VARCHAR(256), authentication  bytea
);
DROP TABLE IF EXISTS oauth_approvals;
CREATE TABLE oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);


INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('mobile', '{bcrypt}$2a$10$gPhlXZfms0EpNHX0.HHptOhoFD1AoxSr/yUIdTqA8vtjeP4zi0DDu', 'http://localhost:8080/code', 'READ,WRITE', '3600', '10000', 'inventory,payment', 'authorization_code,password,refresh_token,implicit', '{}');

 INSERT INTO PERMISSION (NAME) VALUES
 ('create_profile'),
 ('read_profile'),
 ('update_profile'),
 ('delete_profile');

  INSERT INTO role (NAME) VALUES
		('ROLE_admin'),('ROLE_editor'),('ROLE_operator');

 INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES (1,1);
 INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES (2,1);
 INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES (3,1);
 INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES (4,1);
 INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES (2,2);
 INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES (3,2);
INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES (2,3);

 insert into user1 (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('1', 'krish','{bcrypt}$2a$10$ODGwrk2ufy5d7T6afmACwOA/6j6rvXiP5amAMt1YjOQSdEw44QdqG', 'k@krishantha.com', '1', '1', '1', '1');
 insert into  user1 (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('2', 'suranga', '{bcrypt}$2a$10$wQ8vZl3Zm3.zDSIcZEYym.bGq3fPMJXH9k.Vhudcfr6O6KQwDPSt6','k@krishantha.com', '1', '1', '1', '1');
 insert into  user1 (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('3', 'nuwan', '{bcrypt}$2a$10$wQ8vZl3Zm3.zDSIcZEYym.bGq3fPMJXH9k.Vhudcfr6O6KQwDPSt6','k@krishantha.com', '1', '1', '1', '1');
insert into user1 (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('4', 'john.carnell','{bcrypt}$2a$10$ODGwrk2ufy5d7T6afmACwOA/6j6rvXiP5amAMt1YjOQSdEw44QdqG', 'k@john.com', '1', '1', '1', '1');
 insert into  user1 (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('5', 'william.woodward', '{bcrypt}$2a$10$wQ8vZl3Zm3.zDSIcZEYym.bGq3fPMJXH9k.Vhudcfr6O6KQwDPSt6','k@william.com', '1', '1', '1', '1');


INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES   (1, 1);
INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES    (2, 2);
INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES    (3, 3);
INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES    (1, 4);
INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES    (1, 5);

DROP TABLE IF EXISTS user_orgs;

CREATE TABLE user_orgs (
  organization_id   VARCHAR(100)  NOT NULL,
  user_name         VARCHAR(100)   NOT NULL,
  PRIMARY KEY (user_name));


INSERT INTO user_orgs (organization_id, user_name) VALUES ('d1859f1f-4bd7-4593-8654-ea6d9a6a626e', 'john.carnell');
INSERT INTO user_orgs (organization_id, user_name) VALUES ('42d3d4f5-9f33-42f4-8aca-b7519d6af1bb', 'william.woodward');
