CREATE TABLE `administrative_punishment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `union_id` varchar(255) NOT NULL DEFAULT '' COMMENT '信用湖南网站中行政处罚的id',
  `title` varchar(1024) NOT NULL DEFAULT '' COMMENT '案件名称',
  `publish_time_str` varchar(255) NOT NULL DEFAULT '' COMMENT '发布日期（字符串）',
  `publish_time` timestamp NULL DEFAULT NULL COMMENT '发布日期',
  `punish_no` varchar(1024) NOT NULL DEFAULT '' COMMENT '行政处罚决定书文号',
  `subject` varchar(1024) NOT NULL DEFAULT '' COMMENT '行政相对人名称',
  `legal_representative` varchar(1024) NOT NULL DEFAULT '' COMMENT '法定代表人',
  `legal_operation_department` varchar(1024) NOT NULL DEFAULT '' COMMENT '执法部门',
  `punish_time_str` varchar(255) NOT NULL DEFAULT '' COMMENT '作出行政处罚的日期（字符串）',
  `punish_time` timestamp NULL DEFAULT NULL COMMENT '作出行政处罚的日期',
  `content` text COMMENT '行政处罚决定书(全文或摘要)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `data_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据是否有效（删除）0 无效（已删除） 1有效（未删除） ',
  PRIMARY KEY (`id`),
  KEY `publish_time` (`publish_time`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8