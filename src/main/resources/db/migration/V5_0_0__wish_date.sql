
create table if not exists wish_date(
    wish_date_id varchar(255) not null,
    owner varchar(255) not null,
    wish_date date not null,
    primary key(wish_date_id),
    foreign key(owner)
    references user(user_id)
    );
