
CREATE TABLE `tododatabase`.`itbkk_or3` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(108) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description VARCHAR(518) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    assignees VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    status ENUM('NO_STATUS', 'TO_DO', 'DOING', 'DONE') NOT NULL DEFAULT 'NO_STATUS',
    createdOn DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedOn DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CHECK (CHAR_LENGTH(title) <= 108),
    CHECK (CHAR_LENGTH(assignees) <= 30 AND assignees != ' '),
    CHECK (CHAR_LENGTH(description) <= 518)
);

INSERT INTO `itbkk_or3` (`id`,`title`,`description`,`assignees`,`status`,`createdOn`,`updatedOn`) VALUES (1,'TaskTitle1TaskTitle2TaskTitle3TaskTitle4TaskTitle5TaskTitle6TaskTitle7TaskTitle8TaskTitle9TaskTitle0','Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti1Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti2Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti3Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti4Descripti1Descripti2Descripti3Descripti4Descripti5Descripti6Descripti7Descripti8Descripti9Descripti5','Assignees1Assignees2Assignees3','NO_STATUS','2024-04-22 09:00:00','2024-04-22 09:00:00');
INSERT INTO `itbkk_or3` (`id`,`title`,`description`,`assignees`,`status`,`createdOn`,`updatedOn`) VALUES (2,'Repository',NULL,NULL,'TODO','2024-04-22 09:05:00','2024-04-22 14:00:00');
INSERT INTO `itbkk_or3` (`id`,`title`,`description`,`assignees`,`status`,`createdOn`,`updatedOn`) VALUES (3,'ดาต้าเบส','ສ້າງຖານຂໍ້ມູນ','あなた、彼、彼女 (私ではありません)','DOING','2024-04-22 09:10:00','2024-04-25 00:00:00');
INSERT INTO `itbkk_or3` (`id`,`title`,`description`,`assignees`,`status`,`createdOn`,`updatedOn`) VALUES (4,'_Infrastructure_','_Setup containers_','ไก่งวง กับ เพนกวิน','DONE','2024-04-22 09:15:00','2024-04-22 10:00:00');
