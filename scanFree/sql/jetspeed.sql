#-----------------------------------------------------------------------------
#-- Copyright 2004 The Apache Software Foundation
#-- 
#-- Licensed under the Apache License, Version 2.0 (the "License");
#-- you may not use this file except in compliance with the License.
#-- You may obtain a copy of the License at
#-- 
#-- http://www.apache.org/licenses/LICENSE-2.0
#-- 
#-- Unless required by applicable law or agreed to in writing, software
#-- distributed under the License is distributed on an "AS IS" BASIS,
#-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#-- See the License for the specific language governing permissions and
#-- limitations under the License.
#-----------------------------------------------------------------------------
    
# -----------------------------------------------------------------------
# TURBINE_USER
# -----------------------------------------------------------------------
drop table if exists TURBINE_USER;

CREATE TABLE TURBINE_USER
(
                            USER_ID INTEGER NOT NULL AUTO_INCREMENT,
                    LOGIN_NAME VARCHAR (32) NOT NULL,
                    PASSWORD_VALUE VARCHAR (32) NOT NULL,
                    FIRST_NAME VARCHAR (99) NOT NULL,
                    LAST_NAME VARCHAR (99) NOT NULL,
                    EMAIL VARCHAR (99),
                    CONFIRM_VALUE VARCHAR (99),
                    MODIFIED TIMESTAMP,
                    CREATED TIMESTAMP,
                    LAST_LOGIN TIMESTAMP,
                    DISABLED CHAR (1),
                    OBJECTDATA BLOB,
                    PASSWORD_CHANGED TIMESTAMP,
    PRIMARY KEY(USER_ID),
    UNIQUE (LOGIN_NAME)
);

# -----------------------------------------------------------------------
# TURBINE_ROLE
# -----------------------------------------------------------------------
drop table if exists TURBINE_ROLE;

CREATE TABLE TURBINE_ROLE
(
                            ROLE_ID INTEGER NOT NULL AUTO_INCREMENT,
                    ROLE_NAME VARCHAR (99) NOT NULL,
                    OBJECTDATA BLOB,
    PRIMARY KEY(ROLE_ID),
    UNIQUE (ROLE_NAME)
);

# -----------------------------------------------------------------------
# TURBINE_GROUP
# -----------------------------------------------------------------------
drop table if exists TURBINE_GROUP;

CREATE TABLE TURBINE_GROUP
(
                            GROUP_ID INTEGER NOT NULL AUTO_INCREMENT,
                    GROUP_NAME VARCHAR (99) NOT NULL,
                    OBJECTDATA BLOB,
    PRIMARY KEY(GROUP_ID),
    UNIQUE (GROUP_NAME)
);

# -----------------------------------------------------------------------
# TURBINE_PERMISSION
# -----------------------------------------------------------------------
drop table if exists TURBINE_PERMISSION;

CREATE TABLE TURBINE_PERMISSION
(
                            PERMISSION_ID INTEGER NOT NULL AUTO_INCREMENT,
                    PERMISSION_NAME VARCHAR (99) NOT NULL,
                    OBJECTDATA BLOB,
    PRIMARY KEY(PERMISSION_ID),
    UNIQUE (PERMISSION_NAME)
);

# -----------------------------------------------------------------------
# TURBINE_ROLE_PERMISSION
# -----------------------------------------------------------------------
drop table if exists TURBINE_ROLE_PERMISSION;

CREATE TABLE TURBINE_ROLE_PERMISSION
(
        ROLE_ID INTEGER NOT NULL,
        PERMISSION_ID INTEGER NOT NULL,
    PRIMARY KEY(ROLE_ID,PERMISSION_ID),
    FOREIGN KEY (ROLE_ID) REFERENCES TURBINE_ROLE (ROLE_ID),
    FOREIGN KEY (PERMISSION_ID) REFERENCES TURBINE_PERMISSION (PERMISSION_ID)
);

# -----------------------------------------------------------------------
# TURBINE_USER_GROUP_ROLE
# -----------------------------------------------------------------------
drop table if exists TURBINE_USER_GROUP_ROLE;

CREATE TABLE TURBINE_USER_GROUP_ROLE
(
        USER_ID INTEGER NOT NULL,
        GROUP_ID INTEGER NOT NULL,
        ROLE_ID INTEGER NOT NULL,
    PRIMARY KEY(USER_ID,GROUP_ID,ROLE_ID),
    FOREIGN KEY (USER_ID) REFERENCES TURBINE_USER (USER_ID),
    FOREIGN KEY (GROUP_ID) REFERENCES TURBINE_GROUP (GROUP_ID),
    FOREIGN KEY (ROLE_ID) REFERENCES TURBINE_ROLE (ROLE_ID)
);
  

# -----------------------------------------------------------------------
# JETSPEED_USER_PROFILE
# -----------------------------------------------------------------------
drop table if exists JETSPEED_USER_PROFILE;

CREATE TABLE JETSPEED_USER_PROFILE
(
                            PSML_ID INTEGER NOT NULL AUTO_INCREMENT,
                    USER_NAME VARCHAR (32) NOT NULL,
                    MEDIA_TYPE VARCHAR (99),
                    LANGUAGE VARCHAR (2),
                    COUNTRY VARCHAR (2),
                    PAGE VARCHAR (99),
                    PROFILE BLOB,
    PRIMARY KEY(PSML_ID),
    UNIQUE (USER_NAME, MEDIA_TYPE, LANGUAGE, COUNTRY, PAGE)
);

# -----------------------------------------------------------------------
# JETSPEED_GROUP_PROFILE
# -----------------------------------------------------------------------
drop table if exists JETSPEED_GROUP_PROFILE;

CREATE TABLE JETSPEED_GROUP_PROFILE
(
                            PSML_ID INTEGER NOT NULL AUTO_INCREMENT,
                    GROUP_NAME VARCHAR (99) NOT NULL,
                    MEDIA_TYPE VARCHAR (99),
                    LANGUAGE VARCHAR (2),
                    COUNTRY VARCHAR (2),
                    PAGE VARCHAR (99),
                    PROFILE BLOB,
    PRIMARY KEY(PSML_ID),
    UNIQUE (GROUP_NAME, MEDIA_TYPE, LANGUAGE, COUNTRY, PAGE)
);

# -----------------------------------------------------------------------
# JETSPEED_ROLE_PROFILE
# -----------------------------------------------------------------------
drop table if exists JETSPEED_ROLE_PROFILE;

CREATE TABLE JETSPEED_ROLE_PROFILE
(
                            PSML_ID INTEGER NOT NULL AUTO_INCREMENT,
                    ROLE_NAME VARCHAR (99) NOT NULL,
                    MEDIA_TYPE VARCHAR (99),
                    LANGUAGE VARCHAR (2),
                    COUNTRY VARCHAR (2),
                    PAGE VARCHAR (99),
                    PROFILE BLOB,
    PRIMARY KEY(PSML_ID),
    UNIQUE (ROLE_NAME, MEDIA_TYPE, LANGUAGE, COUNTRY, PAGE)
);
 

# -----------------------------------------------------------------------
# COFFEES
# -----------------------------------------------------------------------
drop table if exists COFFEES;

CREATE TABLE COFFEES
(
   COFFEE_ID INTEGER NOT NULL AUTO_INCREMENT,
   COFFEE_NAME VARCHAR (50),
   SUPPLIER_ID INTEGER,
   PRICE FLOAT,
   SALES INTEGER,
   TOTAL INTEGER,
   PRIMARY KEY(COFFEE_ID)
);
    


#-----------------------------------------------------------------------------
#-- EMAIL
#-----------------------------------------------------------------------------
drop table if exists EMAIL_INBOX;

CREATE TABLE EMAIL_INBOX (
  EMAIL_INBOX_ID integer(11) NOT NULL auto_increment,
  MESSAGE_ID varchar(255) default NULL,
  FILENAME varchar(255) default NULL,
  ATTACHMENT longblob,
  READFLAG int(11) default NULL,
  PRIMARY KEY  (EMAIL_INBOX_ID)
);

# -----------------------------------------------------------------------
# portlet
# -----------------------------------------------------------------------
drop table if exists portlet;

CREATE TABLE portlet
(
                    ID BIGINT NOT NULL AUTO_INCREMENT,
                    NAME VARCHAR (128) NOT NULL,
                    HIDDEN INTEGER default 0,
                    CLASSNAME VARCHAR (255),
                    TYPE VARCHAR (32) NOT NULL,
                    APPLICATION INTEGER default 0,
                    PARENT VARCHAR (128),
                    URL VARCHAR (255),
                    CACHED_ON_URL INTEGER default 1,
                    TITLE VARCHAR (128),
                    DESCRIPTION VARCHAR (255),
                    IMAGE VARCHAR (128),
                    SECURITY VARCHAR (128),
    PRIMARY KEY(ID),
    UNIQUE (NAME)
);

# -----------------------------------------------------------------------
# portlet_category
# -----------------------------------------------------------------------
drop table if exists portlet_category;

CREATE TABLE portlet_category
(
                    ID BIGINT NOT NULL AUTO_INCREMENT,
                    NAME VARCHAR (128) NOT NULL,
                    GROUPE VARCHAR (128) NOT NULL,
                    PORTLET_ID BIGINT,
    PRIMARY KEY(ID),
    FOREIGN KEY (PORTLET_ID) REFERENCES portlet (ID)
        ON DELETE CASCADE 
  
);

# -----------------------------------------------------------------------
# portlet_parameter
# -----------------------------------------------------------------------
drop table if exists portlet_parameter;

CREATE TABLE portlet_parameter
(
                    ID BIGINT NOT NULL AUTO_INCREMENT,
                    NAME VARCHAR (128) NOT NULL,
                    VALUE VARCHAR (255) default '0',
                    TYPE VARCHAR (64),
                    HIDDEN INTEGER default 0,
                    CACHED_ON_VALUE INTEGER default 1,
                    CACHED_ON_NAME INTEGER default 1,
                    TITLE VARCHAR (128),
                    DESCRIPTION VARCHAR (255),
                    IMAGE VARCHAR (128),
                    PORTLET_ID BIGINT,
                    SECURITY VARCHAR (128),
    PRIMARY KEY(ID),
    FOREIGN KEY (PORTLET_ID) REFERENCES portlet (ID)
        ON DELETE CASCADE 
  
);

# -----------------------------------------------------------------------
# portlet_mediatype
# -----------------------------------------------------------------------
drop table if exists portlet_mediatype;

CREATE TABLE portlet_mediatype
(
                    ID BIGINT NOT NULL AUTO_INCREMENT,
                    NAME VARCHAR (128) NOT NULL,
                    PORTLET_ID BIGINT,
    PRIMARY KEY(ID),
    FOREIGN KEY (PORTLET_ID) REFERENCES portlet (ID)
        ON DELETE CASCADE 
  
);

# -----------------------------------------------------------------------
# security_entry
# -----------------------------------------------------------------------
drop table if exists security_entry;

CREATE TABLE security_entry
(
                    ID BIGINT NOT NULL AUTO_INCREMENT,
                    NAME VARCHAR (128) NOT NULL,
                    TITLE VARCHAR (128),
                    DESCRIPTION VARCHAR (255),
    PRIMARY KEY(ID),
    UNIQUE (NAME)
);

# -----------------------------------------------------------------------
# security_access
# -----------------------------------------------------------------------
drop table if exists security_access;

CREATE TABLE security_access
(
                    ID BIGINT NOT NULL AUTO_INCREMENT,
                    ACTION VARCHAR (32) NOT NULL,
                    ENTRY_ID BIGINT,
    PRIMARY KEY(ID),
    FOREIGN KEY (ENTRY_ID) REFERENCES security_entry (ID)
        ON DELETE CASCADE 
  
);

# -----------------------------------------------------------------------
# security_allow
# -----------------------------------------------------------------------
drop table if exists security_allow;

CREATE TABLE security_allow
(
                    ID BIGINT NOT NULL AUTO_INCREMENT,
                    ALLOW_TYPE VARCHAR (32) NOT NULL,
                    ALLOW_VALUE VARCHAR (32) NOT NULL,
                    ALLOW_GROUP VARCHAR (32),
                    ACCESS_ID BIGINT,
    PRIMARY KEY(ID),
    FOREIGN KEY (ACCESS_ID) REFERENCES security_access (ID)
        ON DELETE CASCADE 
  
);
  
  
CREATE TABLE JETSPEED_GROUP_PROFILE
(
                            PSML_ID INTEGER NOT NULL AUTO_INCREMENT,
                    GROUP_NAME VARCHAR (99) NOT NULL,
                    MEDIA_TYPE VARCHAR (99),
                    LANGUAGE VARCHAR (2),
                    COUNTRY VARCHAR (2),
                    PAGE VARCHAR (99),
                    PROFILE BLOB,
    PRIMARY KEY(PSML_ID),
    UNIQUE (GROUP_NAME, MEDIA_TYPE, LANGUAGE, COUNTRY, PAGE)
);
  
  
  
  INSERT INTO TURBINE_PERMISSION VALUES(1,'view',NULL);
INSERT INTO TURBINE_PERMISSION VALUES(2,'customize',NULL);
INSERT INTO TURBINE_PERMISSION VALUES(3,'maximize',NULL);
INSERT INTO TURBINE_PERMISSION VALUES(4,'minimize',NULL);
INSERT INTO TURBINE_PERMISSION VALUES(5,'personalize',NULL);
INSERT INTO TURBINE_PERMISSION VALUES(6,'info',NULL);
INSERT INTO TURBINE_PERMISSION VALUES(7,'close',NULL);
INSERT INTO TURBINE_PERMISSION VALUES(8,'detach',NULL);
INSERT INTO TURBINE_ROLE VALUES(1,'user',NULL);
INSERT INTO TURBINE_ROLE VALUES(2,'admin',NULL);
INSERT INTO TURBINE_ROLE VALUES(3,'guest',NULL);
INSERT INTO TURBINE_GROUP VALUES(1,'Jetspeed',NULL);
INSERT INTO TURBINE_GROUP VALUES(2,'apache',NULL);
INSERT INTO TURBINE_USER VALUES(1,'admin','jetspeed','Jetspeed','Admin','admin@jakarta-jetspeed.com','CONFIRMED',NULL,NULL,'2002-10-15 18:45:41.671','F',NULL, NULL);
INSERT INTO TURBINE_USER VALUES(2,'turbine','turbine','Tommy','Turbine','tommy@jakarta-jetspeed.com','CONFIRMED',NULL,NULL,'2002-10-15 18:45:41.671','F',NULL, NULL);
INSERT INTO TURBINE_USER VALUES(3,'anon','anon','Anonymous','User','anon@jakarta-jetspeed.com','CONFIRMED',NULL,NULL,'2002-10-15 18:45:41.671','F',NULL, NULL);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(1,1);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(1,2);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(1,3);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(1,4);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(1,5);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(1,6);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(2,1);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(2,2);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(2,3);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(2,4);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(2,5);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(2,6);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(2,7);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(3,1);
INSERT INTO TURBINE_ROLE_PERMISSION VALUES(3,6);
INSERT INTO TURBINE_USER_GROUP_ROLE VALUES(2,1,1);
INSERT INTO TURBINE_USER_GROUP_ROLE VALUES(1,1,1);
INSERT INTO TURBINE_USER_GROUP_ROLE VALUES(1,1,2);
INSERT INTO TURBINE_USER_GROUP_ROLE VALUES(3,1,3);
INSERT INTO COFFEES VALUES(1,'ColombianGrade',5,7.99,1,2);
INSERT INTO COFFEES VALUES(2,'KonaGrade',6,7.99,1,2);
INSERT INTO COFFEES VALUES(3,'FrenchRoastGrade',7,7.99,1,2);
INSERT INTO COFFEES VALUES(4,'HazelNutGrade',8,7.99,1,2);
INSERT INTO COFFEES VALUES(5,'VanillaGrade',9,7.99,1,2);
INSERT INTO COFFEES VALUES(6,'JavaGrade',10,7.99,1,2);
INSERT INTO COFFEES VALUES(7,'IndonesianGrade',11,7.99,1,2);
INSERT INTO COFFEES VALUES(8,'OotyGrade',1,7.99,1,2);
INSERT INTO COFFEES VALUES(9,'KenyanGrade',2,7.99,1,2);
INSERT INTO COFFEES VALUES(10,'JoeGrade',3,7.99,1,2);
INSERT INTO COFFEES VALUES(11,'CantThinkOfAnymoreGrade',4,7.99,1,2);

  
