CREATE TABLE url_info (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(2000),
    shortcode CHAR(6),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    access_count INT DEFAULT 0
);