create TABLE book(id  BIGINT generated by default as identity (start with 1),
reader varchar(64),
isbn VARCHAR (64),
title VARCHAR (64),
author VARCHAR (64),
description VARCHAR(128),
rank FLOAT,
created_date DATETIME NOT NULL ,
modified_date DATETIME,
  primary key (id)
);