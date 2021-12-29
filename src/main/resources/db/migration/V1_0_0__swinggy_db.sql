CREATE TABLE IF NOT EXISTS region(
    region_id INT(3) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(region_id)
    );
INSERT INTO region VALUES
    (1,"北海道地方"),
    (2,"東北地方"),
    (3,"関東地方"),
    (4,"中部地方"),
    (5,"近畿地方"),
    (6,"中国地方"),
    (7,"四国地方"),
    (8,"九州地方");

CREATE TABLE IF NOT EXISTS prefecture(
    prefecture_id INT(3) NOT NULL,
    region_id INT(3) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(prefecture_id),
    FOREIGN KEY(region_id) REFERENCES region(region_id)
);

INSERT INTO prefecture VALUES
  (1,1,"北海道"),
  (2,2,"青森県"), (3,2,"岩手県"), (4,2,"宮城県"), (5,2,"秋田県"), (6,2,"山形県"), (7,2,"福島県"),
  (8,3,"茨城県"), (9,3,"栃木県"), (10,3,"群馬県"), (11,3,"埼玉県"), (12,3,"千葉県"), (13,3,"東京都"), (14,3,"神奈川県"),
  (15,4,"新潟県"), (16,4,"富山県"), (17,4,"石川県"), (18,4,"福井県"), (19,4,"山梨県"), (20,4,"長野県"), (21,4,"岐阜県"), (22,4,"静岡県"), (23,4,"愛知県"),
  (24,5,"三重県"), (25,5,"滋賀県"), (26,5,"京都府"), (27,5,"大阪府"), (28,5,"兵庫県"), (29,5,"奈良県"), (30,5,"和歌山県"),
  (31,6,"鳥取県"), (32,6,"島根県"), (33,6,"岡山県"), (34,6,"広島県"), (35,6,"山口県"),
  (36,7,"徳島県"), (37,7,"香川県"), (38,7,"愛媛県"), (39,7,"高知県"),
  (40,8,"福岡県"), (41,8,"佐賀県"), (42,8,"長崎県"), (43,8,"熊本県"), (44,8,"大分県"), (45,8,"宮崎県"), (46,8,"鹿児島県"), (47,8,"沖縄県");

CREATE TABLE IF NOT EXISTS user(
    user_id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS user_profile(
    user_profile_id VARCHAR(255),
    user_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    screen_name VARCHAR(255) NOT NULL unique,
    email VARCHAR(255) NOT NULL unique,
    tel VARCHAR(255) unique,
    profile_image_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(user_profile_id),
    FOREIGN KEY(user_id) REFERENCES user(user_id)
);

CREATE TABLE IF NOT EXISTS user_group(
    group_id VARCHAR(255),
    owner VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    group_name VARCHAR(255) NOT NULL,
    PRIMARY KEY(group_id),
    FOREIGN KEY(owner) REFERENCES user(user_id)
);

CREATE TABLE IF NOT EXISTS wish_date(
    wish_date_id VARCHAR(255),
    owner VARCHAR(255) NOT NULL,
    wish_date DATE NOT NULL,
    group_id VARCHAR(255) NOT NULL,
    PRIMARY KEY(wish_date_id),
    FOREIGN KEY(owner) REFERENCES user(user_id),
    FOREIGN KEY(group_id) REFERENCES user_group(group_id)
);

CREATE TABLE IF NOT EXISTS participation(
    participation_id VARCHAR(255),
    wish_date_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    participant VARCHAR(255) NOT NULL,
    PRIMARY KEY(participation_id),
    FOREIGN KEY(wish_date_id) REFERENCES wish_date(wish_date_id),
    FOREIGN KEY(participant) REFERENCES user(user_id)
);

CREATE TABLE IF NOT EXISTS wish_date_comment(
    comment_id VARCHAR(255),
    wish_date_id VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    text VARCHAR(512) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(comment_id),
    FOREIGN KEY(wish_date_id) REFERENCES wish_date(wish_date_id)
);

CREATE TABLE IF NOT EXISTS user_group_member(
    user_group_member_id VARCHAR(255),
    group_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_owner BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY(user_group_member_id),
    FOREIGN KEY(group_id) REFERENCES user_group(group_id),
    FOREIGN KEY(user_id) REFERENCES user(user_id)
);

CREATE TABLE IF NOT EXISTS user_group_thread(
    user_group_thread_id VARCHAR(255),
    group_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(user_group_thread_id),
    FOREIGN KEY(group_id) REFERENCES user_group(group_id)
);

CREATE TABLE IF NOT EXISTS user_group_comment(
    user_group_comment_id VARCHAR(255),
    user_group_thread_id VARCHAR(255) NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    text VARCHAR(512) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(user_group_comment_id),
    FOREIGN KEY(user_group_thread_id) REFERENCES user_group_thread(user_group_thread_id),
    FOREIGN KEY(member_id) REFERENCES user_group_member(user_group_member_id)
);

