select id,contentType,content,description,emails,httpResponse,keyword,title,url,headerHttp from urlmin8
where id>96000
where id>1711


select count(1) from url


select max(id) from url
where dateScan=max(dateScan) 


select url from url where content like %html%


delete from url where id>94672

alter table url add content text

create index id_index on url(id)

CREATE TABLE urlmin8 (
  id int(10) unsigned NOT NULL auto_increment,
  url varchar(30) NOT NULL default '',
  title varchar(50) default NULL,
  keyword varchar(100) default NULL,
  content text,
  emails varchar(50) default NULL,
  httpResponse smallint(6) default NULL,
  dateScan date default NULL,
  description varchar(100) default NULL,
  mails varchar(100) default NULL,
  contentType varchar(20) default NULL,
  headerHttp text,
  PRIMARY KEY  (id),
  KEY url_index (url),
  KEY id_index (id)
) TYPE=MyISAM;
