create table task_list (
  id varchar(64) not null primary key,
  created timestamp not null,
  last_updated timestamp
);

create table task_list_item (
  list_id varchar(64) not null,
  item_index int not null,
  text varchar(1024)
);
alter table task_list_item add primary key (list_id, item_index);
