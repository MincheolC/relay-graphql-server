CREATE TABLE `follows` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `followee_id` int NOT NULL COMMENT "유저가 팔로우 하는 유저 아이디",
    PRIMARY KEY (id)
);
--;;
CREATE INDEX follows_user_id_idx ON follows (user_id);
--;;
CREATE INDEX follows_followee_id_idx ON follows (followee_id);
