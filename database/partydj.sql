## CREATE

create table queue_item (queue_item_id varchar(255) not null, queue_item_index bigint not null, played bit not null, title varchar(255) not null, youtube_url varchar(255), something_else varchar(255), station_station_id varchar(255), primary key (queue_item_id))
create table station (station_id varchar(255) not null, name varchar(255) not null, url varchar(255) not null, primary key (station_id))
create table station_queue_item (station_id varchar(255) not null, queue_item_id varchar(255) not null)
alter table station add constraint UK_gnneuc0peq2qi08yftdjhy7ok unique (name)
alter table station_queue_item add constraint UK_5jo1jx00lk32ivlht00k2dbkc unique (queue_item_id)
alter table queue_item add constraint FK4pajiom1uov9u98qkv3w7nbs9 foreign key (station_station_id) references station (station_id)
alter table station_queue_item add constraint FK7t390r5i95brtdrjiqn3l8yv3 foreign key (queue_item_id) references queue_item (queue_item_id)
alter table station_queue_item add constraint FK3n2mv2jhfxdfms93ie82vgxta foreign key (station_id) references station (station_id)
