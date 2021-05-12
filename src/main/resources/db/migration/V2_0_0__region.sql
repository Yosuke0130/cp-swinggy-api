
create table if not exists region(
    id int(3) not null,
    name varchar(255) not null,
    primary key(id)
    );

insert into region values
      (1,"北海道地方"),
      (2,"東北地方"),
      (3,"関東地方"),
      (4,"中部地方"),
      (5,"近畿地方"),
      (6,"中国地方"),
      (7,"四国地方"),
      (8,"九州地方");
