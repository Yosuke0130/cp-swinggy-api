
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