
create table if not exists user_profile(
    user_profile_id int(3),
    user_id int(3) not null,
    name varchar(255) not null,
    screen_name varchar(255) not null unique,
    email varchar(255) not null,
    image blob,
    tel varchar(255) not null,
    primary key(user_profile_id),
    constraint fk_user_id
     foreign key(user_id)
     references user(user_id)
    );
