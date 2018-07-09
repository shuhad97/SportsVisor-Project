BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `User` (
	`User`	TEXT,
	`Password`	TEXT
);
INSERT INTO `User` VALUES ('admin','admin');
INSERT INTO `User` VALUES ('test','test');
COMMIT;
