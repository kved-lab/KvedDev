select id,contentType,content,description,emails,httpResponse,keyword,title,url,headerHttp from url
where id=28400
where id>1711

update url set url = 'edaaa.skyrock.com/' where url ='edaaa.skyrock.com'
SELECT url FROM url ORDER BY ID
SELECT id,url,title,description,keyword FROM url ORDER BY id ASC LIMIT 15000, 50
select url from url where url like '%free.fr' and id>103020

select url from url where url REGEXP '.*[.].*[.]skyrock.com'


select id,url from url where url like'x%' and CHAR_LENGTH(url)=14 order by id

select max(id) from url
where dateScan=max(dateScan) 


select count(1) from url where title <>'Index of /'


delete from url where id>284459

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

alter table url modify column title VARCHAR(100)

select url from url where content like '%films%' and content like '%mpeg%'

select * from url limit 100, 200


