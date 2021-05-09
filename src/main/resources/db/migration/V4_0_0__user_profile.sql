
create table if not exists user_profile(
    user_profile_id varchar(255),
    user_id int(3) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    screen_name varchar(255) not null unique,
    email varchar(255) not null unique,
    tel varchar(255) not null unique,
    profile_image_path varchar(255),
    primary key(user_profile_id),
    constraint fk_user_id
     foreign key(user_id)
     references user(user_id)
    );
