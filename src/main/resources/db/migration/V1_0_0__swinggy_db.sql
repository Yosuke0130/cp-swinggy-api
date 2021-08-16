create table if not exists region(
    region_id int(3) not null,
    name varchar(255) not null,
    primary key(region_id)
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

create table if not exists prefecture(
    prefecture_id int(3) not null,
    region_id int(3) not null,
    name varchar(255) not null,
    primary key(prefecture_id),
    foreign key(region_id) references region(region_id)
);

insert into prefecture values
  (1,1,"北海道"),
  (2,2,"青森県"),
  (3,2,"岩手県"),
  (4,2,"宮城県"),
  (5,2,"秋田県"),
  (6,2,"山形県"),
  (7,2,"福島県"),
  (8,3,"茨城県"),
  (9,3,"栃木県"),
  (10,3,"群馬県"),
  (11,3,"埼玉県"),
  (12,3,"千葉県"),
  (13,3,"東京都"),
  (14,3,"神奈川県"),
  (15,4,"新潟県"),
  (16,4,"富山県"),
  (17,4,"石川県"),
  (18,4,"福井県"),
  (19,4,"山梨県"),
  (20,4,"長野県"),
  (21,4,"岐阜県"),
  (22,4,"静岡県"),
  (23,4,"愛知県"),
  (24,5,"三重県"),
  (25,5,"滋賀県"),
  (26,5,"京都府"),
  (27,5,"大阪府"),
  (28,5,"兵庫県"),
  (29,5,"奈良県"),
  (30,5,"和歌山県"),
  (31,6,"鳥取県"),
  (32,6,"島根県"),
  (33,6,"岡山県"),
  (34,6,"広島県"),
  (35,6,"山口県"),
  (36,7,"徳島県"),
  (37,7,"香川県"),
  (38,7,"愛媛県"),
  (39,7,"高知県"),
  (40,8,"福岡県"),
  (41,8,"佐賀県"),
  (42,8,"長崎県"),
  (43,8,"熊本県"),
  (44,8,"大分県"),
  (45,8,"宮崎県"),
  (46,8,"鹿児島県"),
  (47,8,"沖縄県");

create table if not exists user(
    user_id varchar(255),
    created_at date,
    primary key(user_id)
    );

create table if not exists user_profile(
    user_profile_id varchar(255),
    user_id varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    screen_name varchar(255) not null unique,
    email varchar(255) not null unique,
    tel varchar(255) unique,
    profile_image_path varchar(255),
    primary key(user_profile_id),
     foreign key(user_id)
     references user(user_id)
    );

create table if not exists wish_date(
    wish_date_id varchar(255) not null,
    owner varchar(255) not null,
    wish_date date not null,
    primary key(wish_date_id),
    foreign key(owner)
    references user(user_id)
    );

create table if not exists participation(
    participation_id varchar(255) not null,
    wish_date_id varchar(255) not null,
    created_at datetime not null,
    participant varchar(255) not null,
    primary key(participation_id),
    foreign key(wish_date_id)
    references wish_date(wish_date_id),
    foreign key(participant)
    references user(User_id)
    );
