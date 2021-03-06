create schema if not exists store;
use store;

CREATE TABLE if not exists store.url_store
(
id int(10) NOT NULL AUTO_INCREMENT COMMENT '아이디',
origin_url varchar(500) NOT NULL COMMENT '원본 URL',
short_url varchar(10) NOT NULL COMMENT '변경 URL',
PRIMARY KEY (id)
);

CREATE TABLE if not exists store.url_count_board
(
id int(10) NOT NULL COMMENT '아이디',
request_count int(10) NOT NULL COMMENT '요청횟수',
PRIMARY KEY (id)
);

