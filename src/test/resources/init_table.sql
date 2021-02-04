-- ----------------------------
-- Table structure for job_title
-- ----------------------------
DROP TABLE IF EXISTS `job_title`;
CREATE TABLE `job_title` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
`name` varchar(45) NOT NULL COMMENT '名称',
`is_enabled` tinyint(2) NOT NULL COMMENT '是否停用：0启用 1停用',
`code` varchar(20) NOT NULL COMMENT '值',
`remark` varchar(20) DEFAULT NULL COMMENT '备注',
`sort` int(11) DEFAULT NULL COMMENT '显示次序',
`is_delete` tinyint(2) NOT NULL COMMENT '是否删除：0未删 1已删',
`access_id` bigint(20) NOT NULL COMMENT '接入商id',
PRIMARY KEY (`id`)
);