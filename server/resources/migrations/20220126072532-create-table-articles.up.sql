CREATE TABLE `articles` (
    `id` int NOT NULL AUTO_INCREMENT,
    `author_id` INT NOT NULL,
    `title` varchar(100) NOT NULL,
    `content` text,
    `hash_tag` varchar(500),
    `like_count` INT COMMENT "좋아요 수",
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
