# Host: localhost  (Version: 5.7.3-m13-log)
# Date: 2014-09-13 08:30:56
# Generator: MySQL-Front 5.3  (Build 2.53)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

#
# Source for table "bas_dict_t"
#

DROP TABLE IF EXISTS `bas_dict_t`;
CREATE TABLE `bas_dict_t` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_is_editable` varchar(255) DEFAULT NULL,
  `dict_item` varchar(255) DEFAULT NULL,
  `dict_type` varchar(255) DEFAULT NULL,
  `dict_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

#
# Data for table "bas_dict_t"
#

INSERT INTO `bas_dict_t` VALUES (1,'是','战略合作伙伴','企业客户等级','4'),(2,'是','咨询','服务类型','咨询'),(3,'是','合作伙伴','企业客户等级','3'),(4,'是','大客户','企业客户等级','2'),(5,'是','重点开发客户','企业客户等级','1'),(6,'是','普通客户','企业客户等级','0'),(7,'是','投诉','服务类型','投诉'),(8,'是','建议','服务类型','建议');

#
# Source for table "bas_product_t"
#

DROP TABLE IF EXISTS `bas_product_t`;
CREATE TABLE `bas_product_t` (
  `pro_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_depot` varchar(255) DEFAULT NULL,
  `pro_goods_unit` varchar(255) DEFAULT NULL,
  `pro_name` varchar(255) DEFAULT NULL,
  `pro_no` varchar(255) DEFAULT NULL,
  `pro_price` varchar(255) DEFAULT NULL,
  `pro_rank` varchar(255) DEFAULT NULL,
  `pro_remark` varchar(255) DEFAULT NULL,
  `pro_type` varchar(255) DEFAULT NULL,
  `pro_unit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

#
# Data for table "bas_product_t"
#

INSERT INTO `bas_product_t` VALUES (1,'2号仓','台','三星电脑','490','3999','1','耐用','FF-434yy','台'),(2,'1号仓库','台','联想电脑','400','3899','3','嗯','we#$-sf342','台'),(3,'3号仓库','台','大雄电视','3570','6999','3','像素高','SR343-fds38','太'),(4,'深圳宝安区','台','小璇手机','678','3999','1','耐用','FF-434241','500'),(5,'2号仓','部','大雄相机','4500','5000','2','清晰','B-XX3434','部'),(6,'2号仓','台','美的电视','3435','4999','3','一般2','CC-34fs899','台'),(7,'1号仓库','台','iphone6','3566','5999','1','还可以','豪华仓库','台'),(8,'2号仓','台','端端Ipad','348','8888','1','特别好用','DD-567df567','台'),(9,'3号仓库','台','大雄手机','856','5000','2','可以','B-XX34f34','台'),(10,'深圳宝安区','台','王大璇数字电视','345','6788','1','霸气侧漏','D-X3619','台'),(11,'1号仓库','台','王牌iPad','54','4999','2','耐用','CGf-34fs899','台'),(12,'深圳宝安区','台','小熊笔记本','5466','6889','2','小巧','JTG-3246','台'),(13,'2号仓','台','小銮智能手机','565','3445','1','耐用','D-X3619df','台'),(14,'1号仓库','台','大銮电视','856','4566','2','像素高','CGf-34fs89000','台'),(15,'3号仓库','台','秘密手机','8578','3445','1','耐用','dOO-dfa43','台'),(16,'1号仓库','台','美的电饭锅','876','645','1','耐用','ERE-424','个'),(17,'3号仓库','台','小超洗衣机','234','3445','2','耐用','Cs00-dfs123','台'),(18,'3号仓库','台','超超笔记本','543','3999','1','性能高','DGH-56Uv64','台'),(19,'2号仓','台','超超手机','3543','6453','1','耐用','TT-df4856','台'),(20,'1号仓库','台','菲菲电脑','453','4999','3','耐用','ST-si43f','台');

#
# Source for table "cst_customer_t"
#

DROP TABLE IF EXISTS `cst_customer_t`;
CREATE TABLE `cst_customer_t` (
  `cst_id` int(11) NOT NULL AUTO_INCREMENT,
  `cst_addr` varchar(255) DEFAULT NULL,
  `cst_bank` varchar(255) DEFAULT NULL,
  `cst_bank_account` varchar(255) DEFAULT NULL,
  `cst_bankroll` int(11) NOT NULL,
  `cst_chieftain` varchar(255) DEFAULT NULL,
  `cst_credit` int(11) NOT NULL,
  `cst_fax` varchar(255) DEFAULT NULL,
  `cst_level` int(11) NOT NULL,
  `cst_level_label` varchar(255) DEFAULT NULL,
  `cst_licence_no` varchar(255) DEFAULT NULL,
  `cst_local_tax_no` varchar(255) DEFAULT NULL,
  `cst_manager_id` int(11) NOT NULL,
  `cst_name` varchar(255) DEFAULT NULL,
  `cst_national_tax_no` varchar(255) DEFAULT NULL,
  `cst_region` varchar(255) DEFAULT NULL,
  `cst_satisfy` int(11) NOT NULL,
  `cst_tel` varchar(255) DEFAULT NULL,
  `cst_turnover` int(11) NOT NULL,
  `cst_website` varchar(255) DEFAULT NULL,
  `cst_zip` varchar(255) DEFAULT NULL,
  `cust_manager_name` varchar(255) DEFAULT NULL,
  `cst_status` int(11) NOT NULL,
  PRIMARY KEY (`cst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

#
# Data for table "cst_customer_t"
#

INSERT INTO `cst_customer_t` VALUES (1,'深圳市罗湖区','中国银行','213213213',100,'杰雄',2,'0755-3325484',1,'重点开发客户','21321312','123213213',0,'青山科技有限公司','2131231','深圳',0,'0755-3325484',10000000,'www.baidu.com','518000','小红',0),(2,'广东深圳福田','中国银行','11001100',0,'小黄',4,'0755-12345678',1,'重点开发客户','','',0,'五月天投资有限公司','','北京',1,'0755-12345678',500000,'www.SZ.com','518040','胡杰雄',0),(3,'广州市海珠区昌岗东路','光大银行','12124545454545',0,'郑泳汛',0,'020-12345688',0,'普通客户','','',0,'爆米花通讯设备有限公司','','北京',2,'020-12345688',0,'www.gzgzgm.com','020020','小红',0),(4,'广东省深圳市龙华新区拘留所','平安银行','4565465454545454',0,'吴小鑫',2,'0755-34563673',3,'合作伙伴','','',0,'方歪集团','','深圳',0,'0755-12121445',0,'www.xxx.com','518040','杰雄',0),(5,'广东省深圳市宝安区西乡精神病医院','平安银行','45794212345465454',0,'吴中鑫',0,'0755-45878546',0,'普通客户','','',0,'企鹅集团','','北京',0,'0755-45878546',0,'www.szb.com','518040','小红',0),(6,'深圳市福田区香蜜湖人行天桥底','渣打','8888888888888888',999999999,'吴大鑫',0,'45646541321',0,'普通客户','156841321','165132165',0,'黄河实业有限公司','15165','北京',0,'0755-78978978',0,'www.sb.com','518000','胡杰雄',0),(7,'北京市中南海','农业银行','15645610561651',9851561,'胡小熊',0,'010-32111231',0,'普通客户','21654165165','161561651',0,'康帅傅控股有限公司','1561621651','北京',0,'010-32111231',48888,'www.32165.com','518000','胡杰雄',0),(8,'北京市中东海','朝鲜人民银行','41561351561561',0,'胡大熊',0,'010-88888888',0,'普通客户','5641651321','1341512',0,'茅合酒厂有限责任公司','1215153','北京',0,'010-88888888',0,'www.sbsb.com','1465131','胡杰雄',0),(9,'北京市东城区','渤海银行','11896635544124566',10000000,'王大璇',0,'010-45512235',0,'普通客户','','',0,'王大璇老干妈食品有限公司','','北京',0,'010-45512235',500000,'www.sb.com','001100','胡杰雄',0),(10,'广东省深圳市罗湖区罗湖汽车客运站','交通银行','',0,'吴小鑫',2,'0755-88855463',1,'重点开发客户','','',0,'深圳市运发交通运输集团','','北京',1,'0755-88855463',0,'www.yfjt.com','518001','洪学超',0),(11,'广东省深圳市罗湖区罗湖汽车客运站','交通银行','',0,'吴小鑫',0,'0755-88855463',0,'普通客户','','',0,'深圳市运发交通运输集团','','深圳',1,'0755-88855463',0,'www.yfjt.com','518001','洪学超',0),(12,'北京市朝阳区18号','北京银行','1231545215487454564',50,'王小姐',2,'0354-3569452',2,'大客户','253653536','365365354',0,'北京科技有限公司','3653645324','北京',2,'12596368475',80,'www.beijingt.com','001000','吴凯❤',0),(13,'深圳市罗湖区','','',100,'杰雄',1,'',0,'普通客户','4405','',0,'雄讯XX','','北京',1,'123456789',10000000,'www.baidu.com','518000','hongxuechao',0),(14,'深圳市罗湖区','','',100,'杰雄',3,'0755-34324324',1,'重点开发客户','','',0,'腾讯蛋蛋','','北京',3,'123456789',10000000,'www.baidu.com','518000','杰雄',0);

#
# Source for table "cst_activity_t"
#

DROP TABLE IF EXISTS `cst_activity_t`;
CREATE TABLE `cst_activity_t` (
  `atv_id` int(11) NOT NULL AUTO_INCREMENT,
  `atv_date` datetime DEFAULT NULL,
  `atv_desc` varchar(255) DEFAULT NULL,
  `atv_detail` text,
  `atv_place` varchar(255) DEFAULT NULL,
  `atv_title` varchar(255) DEFAULT NULL,
  `cst_customer_t_cst_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`atv_id`),
  KEY `FK79606C7F976CA1F7` (`cst_customer_t_cst_id`),
  CONSTRAINT `FK79606C7F976CA1F7` FOREIGN KEY (`cst_customer_t_cst_id`) REFERENCES `cst_customer_t` (`cst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

#
# Data for table "cst_activity_t"
#

INSERT INTO `cst_activity_t` VALUES (1,'2014-05-06 00:00:00','无','600','深圳','签约',1),(2,'2014-05-06 00:00:00','无','深圳中心城','深圳福田','交流技术',1),(3,'2014-05-06 00:00:00','无发','技术交流发','深圳','签约',1),(4,'2014-08-10 00:00:00','','','罗湖汽车站','无线设备',10),(5,'2014-08-10 00:00:00','','','罗湖汽车站','无线设备',10),(6,'2014-08-10 00:00:00','','','罗湖汽车站','无线设备',10),(7,NULL,'','','南山腾讯大厦','服务器租赁',5),(8,NULL,'','','','购买老干妈辣椒酱',9),(9,NULL,'','','贵州','签约',6),(10,'2014-09-12 19:42:19','无','啊哈哈发发f发','深圳市福田区凤凰大厦','签约',2),(11,NULL,'','','北京中南海','讨论国家大事',3),(12,NULL,'','','湾湾','购买康帅傅',7),(13,'2014-05-06 00:00:00','','','','',12),(19,'2014-06-03 00:00:00','无','见面正式简约','人民光藏那个','签约',12),(20,'2014-06-03 00:00:00','无','见面正式简约','人民光藏那个','签约',12),(24,'2014-09-12 19:42:19','无','发达省份发','深圳福田','签约',2),(25,'2014-09-12 19:54:23','发达省份','发达省份','发大水发','发达省份',2),(26,'2014-09-12 19:55:53','对方萨芬','放大 ','的萨芬','发大水',1),(27,'2014-09-12 19:58:23','的萨芬发','发大水','的萨芬','fdas ',2);

#
# Source for table "cst_indent_t"
#

DROP TABLE IF EXISTS `cst_indent_t`;
CREATE TABLE `cst_indent_t` (
  `indent_id` int(11) NOT NULL AUTO_INCREMENT,
  `indent_date` datetime DEFAULT NULL,
  `indent_destination` varchar(255) DEFAULT NULL,
  `indent_state` varchar(255) DEFAULT NULL,
  `indent_sum` double DEFAULT NULL,
  `cst_customer_t_cst_id` int(11) DEFAULT NULL,
  `indent_name` varchar(255) DEFAULT NULL,
  `indent_create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`indent_id`),
  KEY `FKA397899C976CA1F7` (`cst_customer_t_cst_id`),
  CONSTRAINT `FKA397899C976CA1F7` FOREIGN KEY (`cst_customer_t_cst_id`) REFERENCES `cst_customer_t` (`cst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

#
# Data for table "cst_indent_t"
#

INSERT INTO `cst_indent_t` VALUES (1,'2013-05-06 00:00:00','深圳市地王大厦','未回款',1000000,3,'老干妈',NULL),(3,'2013-08-06 00:00:00','哈韩','已汇款',10,3,'老干妈',NULL),(4,'2013-05-06 00:00:00','深圳市罗湖区地王大厦','已汇款',10000000,3,'联想电脑进购',NULL),(5,'2014-09-13 00:31:43','深圳市罗湖区地王大厦','未汇款',1000,5,'qq使用教材',NULL),(6,'2014-09-13 00:46:28','广东省广州市天河区','未回款',50000,4,'宏基电脑',NULL),(7,'2014-09-13 00:51:15','深圳市某某地方','已汇款',10000,10,'智能手机',NULL),(8,'2014-09-13 00:53:58','深圳市某某小镇','未回款',60000,11,'数字电视',NULL),(9,'2014-09-13 00:55:15','北京市某某小道','已汇款',40000,12,'数码相机',NULL),(10,'2014-09-13 00:55:57','天津市爆米花路','未回款',300000,3,'数字电视',NULL),(11,'2014-09-13 00:57:02','肇庆市端州区肇庆学院','已汇款',40000,2,'联想电脑',NULL),(12,'2014-09-13 01:00:59','苹果山-你是我的小呀小苹果','未回款',20000,5,'苹果手机',NULL),(13,'2014-09-13 01:02:39','广东省汕头市澄海区小端家里','未回款',34099,6,'小苹果电视',NULL),(14,'2014-09-13 01:03:56','贵州茅台酒厂','已汇款',49930,8,'小丫小苹果相机',NULL),(15,'2014-09-13 01:05:52','广东省肇庆学院软件学院','已汇款',34234,9,'王妈牌洗衣机',NULL),(16,'2014-09-13 01:08:04','深圳市熊熊路熊熊家','未回款',34323,11,'熊熊牌多线程笔记本',NULL),(17,'2014-09-13 01:09:39','湖北省大雄路大雄家','已汇款',3008,9,'大雄电视',NULL),(18,'2012-05-06 00:00:00','广东省肇庆学院','已汇款',500000,9,'50顿老干妈辣椒酱',NULL);

#
# Source for table "cst_linkman_t"
#

DROP TABLE IF EXISTS `cst_linkman_t`;
CREATE TABLE `cst_linkman_t` (
  `link_id` int(11) NOT NULL AUTO_INCREMENT,
  `link_cst_id` int(11) NOT NULL,
  `link_cst_name` varchar(255) DEFAULT NULL,
  `link_memo` varchar(255) DEFAULT NULL,
  `link_mobile` varchar(255) DEFAULT NULL,
  `link_name` varchar(255) DEFAULT NULL,
  `link_postion` varchar(255) DEFAULT NULL,
  `link_sex` varchar(255) DEFAULT NULL,
  `link_tel` varchar(255) DEFAULT NULL,
  `cst_customer_t_cst_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`link_id`),
  KEY `FK9FE5F67A976CA1F7` (`cst_customer_t_cst_id`),
  CONSTRAINT `FK9FE5F67A976CA1F7` FOREIGN KEY (`cst_customer_t_cst_id`) REFERENCES `cst_customer_t` (`cst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

#
# Data for table "cst_linkman_t"
#

INSERT INTO `cst_linkman_t` VALUES (1,1,'哈韩','34324','12346785','小王','经理',NULL,'342342423',NULL),(2,1,'哈韩','无','12346785','小名','经理',NULL,'342342423',NULL),(3,0,NULL,'无','12346785','小黄','经理',NULL,'342342423',1),(4,1,'哈韩','无','1234678533','小洪','助理',NULL,'342342423',NULL),(5,1,'哈韩','无','12346785','小王2','经理',NULL,'',NULL),(6,0,NULL,'无','12346785','小黄2','经理',NULL,'',1),(7,0,NULL,'无','12346785','小王2','助理2',NULL,'',1),(8,0,NULL,'无','12346785','小王2','助理',NULL,'',1),(9,0,NULL,'无','12346785','小王','经理',NULL,'342342423',1),(10,0,NULL,'无','12346785','小名','助理',NULL,'',1),(11,0,NULL,'进行业务联系','15496366995','王小姐','业务经理',NULL,'3659876',12);

#
# Source for table "cst_lst_t"
#

DROP TABLE IF EXISTS `cst_lst_t`;
CREATE TABLE `cst_lst_t` (
  `lst_id` int(11) NOT NULL AUTO_INCREMENT,
  `lst_cst_id` varchar(255) DEFAULT NULL,
  `lst_cst_manager_id` varchar(255) DEFAULT NULL,
  `lst_cst_manager_name` varchar(255) DEFAULT NULL,
  `lst_cst_name` varchar(255) DEFAULT NULL,
  `lst_delay` varchar(255) DEFAULT NULL,
  `lst_last_order_date` varchar(255) DEFAULT NULL,
  `lst_reason` varchar(255) DEFAULT NULL,
  `lst_status` varchar(255) DEFAULT NULL,
  `sureLstDate` datetime DEFAULT NULL,
  PRIMARY KEY (`lst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "cst_lst_t"
#

INSERT INTO `cst_lst_t` VALUES (5,NULL,'0','胡杰雄','深圳市电信有限公司','继续沟通','2013-08-06 00:00:00','客户地址厂迁移','确认流失','2014-09-12 15:02:37'),(6,NULL,'0','胡杰雄','深圳市电信有限公司','继续沟通','2013-08-06 00:00:00',NULL,NULL,NULL);

#
# Source for table "cst_service_t"
#

DROP TABLE IF EXISTS `cst_service_t`;
CREATE TABLE `cst_service_t` (
  `svc_id` int(11) NOT NULL AUTO_INCREMENT,
  `svc_create_by` varchar(255) DEFAULT NULL,
  `svc_create_id` int(11) NOT NULL,
  `svc_cst_name` varchar(255) DEFAULT NULL,
  `svc_deal` varchar(255) DEFAULT NULL,
  `svc_deal_by` varchar(255) DEFAULT NULL,
  `svc_deal_date` varchar(255) DEFAULT NULL,
  `svc_deal_id` int(11) NOT NULL,
  `svc_due_date` datetime DEFAULT NULL,
  `svc_due_id` int(11) NOT NULL,
  `svc_due_to` varchar(255) DEFAULT NULL,
  `svc_request` varchar(255) DEFAULT NULL,
  `svc_result` varchar(255) DEFAULT NULL,
  `svc_satisfy` varchar(255) DEFAULT NULL,
  `svc_status` varchar(255) DEFAULT NULL,
  `svc_type` varchar(255) DEFAULT NULL,
  `svc_create_date` datetime DEFAULT NULL,
  `svc_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`svc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

#
# Data for table "cst_service_t"
#

INSERT INTO `cst_service_t` VALUES (7,'',0,'太阳药业','回答问题了','小明','2014-9-12 18:49:12',0,'2014-09-11 21:14:30',0,'胡杰雄','运费收取问题',NULL,NULL,'已分配','咨询','2014-09-11 21:14:08','询问收音机运费收费方式'),(8,'',0,'深圳北火车站',NULL,NULL,NULL,0,NULL,0,NULL,'',NULL,NULL,'新创建','投诉','2014-09-12 09:01:48','旅客投诉网络不稳定'),(9,'',0,'深圳宝安国际机场',NULL,NULL,NULL,0,NULL,0,NULL,'',NULL,NULL,'新创建','投诉','2014-09-12 09:03:09','应用程序闪退'),(10,'',0,'青山科技有限公司',NULL,NULL,NULL,0,NULL,0,NULL,'',NULL,NULL,'新创建','投诉','2014-09-12 09:01:52','服务态度差'),(11,'',0,'深圳市国家税务局',NULL,NULL,NULL,0,NULL,0,NULL,'',NULL,NULL,'新创建','建议','2014-09-12 09:03:35','中秋节礼数不够、局长要求八菜一汤、路易十三洋酒'),(12,'',0,'王大璇老干妈食品有限公司',NULL,NULL,NULL,0,NULL,0,NULL,'',NULL,NULL,'新创建','咨询','2014-09-12 09:04:09','产品类型和价格'),(13,'',0,'深圳海关',NULL,NULL,NULL,0,NULL,0,NULL,'',NULL,NULL,'新创建','建议','2014-09-12 09:05:02','交“年费”走免申报通道、关务员可下令不开货仓'),(14,'',0,'王大璇老干妈食品有限公司',NULL,NULL,NULL,0,NULL,0,NULL,'',NULL,NULL,'新创建','咨询','2014-09-12 09:08:14','辣椒酱够不够劲？'),(16,'',0,'北京国际科技公司','正在进行删选','','2014-9-12 15:26:36',0,'2014-09-12 15:13:19',0,'胡杰雄','给出电脑价格 并筛选出最适合家庭使用的电脑','满意','☆☆☆☆','已分配','咨询','2014-09-12 15:12:22','咨询电脑的价格'),(17,'',0,'广州科技股份有限公司','正在处理','','2014-9-12 15:31:36',0,'2014-09-12 15:31:20',0,'小红','介绍产品','一般','☆','已分配','咨询','2014-09-12 15:30:15','介绍产品');

#
# Source for table "sal_chance_t"
#

DROP TABLE IF EXISTS `sal_chance_t`;
CREATE TABLE `sal_chance_t` (
  `chc_id` int(11) NOT NULL AUTO_INCREMENT,
  `chc_create_by` varchar(255) DEFAULT NULL,
  `chc_create_date` datetime DEFAULT NULL,
  `chc_create_id` int(11) NOT NULL,
  `chc_cust_name` varchar(255) DEFAULT NULL,
  `chc_desc` varchar(255) DEFAULT NULL,
  `chc_due_date` datetime DEFAULT NULL,
  `chc_due_id` int(11) NOT NULL,
  `chc_due_to` int(11) NOT NULL,
  `chc_linkman` varchar(255) DEFAULT NULL,
  `chc_rate` int(11) NOT NULL,
  `chc_source` varchar(255) DEFAULT NULL,
  `chc_status` varchar(255) DEFAULT NULL,
  `chc_tel` varchar(255) DEFAULT NULL,
  `chc_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`chc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

#
# Data for table "sal_chance_t"
#

INSERT INTO `sal_chance_t` VALUES (1,'','2014-09-12 14:50:26',0,'深圳电信有限公司','合作','2014-09-12 15:02:45',0,4,'王小璇',0,'上网','开发成功','18424234234','无线项目合作'),(2,'','2014-09-12 13:58:18',0,'深圳电信研究院','哈哈1','2014-09-12 18:48:55',0,3,'先锋',100,'微博',NULL,'15005455168','无线项目合作'),(3,'','2014-09-12 09:21:00',0,'中国电信广东分公司','',NULL,0,0,'洪超人',0,'',NULL,'13544562156','无线项目升级'),(4,'','2014-09-12 15:36:28',0,'深圳市人民政府','长期向我公司订购设备。','2014-09-12 08:56:54',0,0,'王荣',0,'',NULL,'15354156842','购买设备'),(5,'','2014-09-12 08:46:46',0,'广深沿江高速公路（深圳段）运营有限公司','','2014-09-12 09:13:52',0,5,'陈先生',0,'',NULL,'15988869456','全路段移动信号覆盖'),(6,'','2014-09-11 21:31:03',0,'广铁集团深圳北火车站','4G网络建设',NULL,0,0,'刘先生',0,'',NULL,'13658966452','4G网络建设'),(7,'','2014-09-12 09:21:15',0,'深圳宝安国际机场','',NULL,0,0,'黄先生',0,'',NULL,'13745869663','航空信息APP开发'),(8,'','2014-09-12 09:18:01',0,'京港澳高速广深段（大观-皇岗）管理公司','',NULL,0,0,'郑先生',0,'',NULL,'13905548696','项目规划'),(9,'','2014-09-12 08:58:42',0,'深圳海关皇岗口岸福田保税区','',NULL,0,0,'洪关长',0,'',NULL,'15966984587','来往深港走私'),(10,'','2014-09-12 08:58:52',0,'深圳市国家税务局','',NULL,0,0,'洪局长',0,'',NULL,'15966348965','开具假发票、骗税漏税'),(11,'','2014-09-12 09:23:40',0,'深圳市工商行政管理局','',NULL,0,0,'吴菊长',0,'',NULL,'13456915896','为注册空壳公司、审批验资提供便利、快速发营业执照'),(12,'','2014-09-12 09:28:07',0,'深圳市公安局宝安分局西乡交警大队','',NULL,0,0,'吴大队长',0,'',NULL,'13612345889','驾驶证代扣分'),(13,'','2014-09-12 13:54:56',0,'睿智技术有限公司','有比较大的几率','2014-09-12 13:54:17',0,0,'朱小姐',70,'报纸',NULL,'13533639475','采买电脑'),(14,'','2014-09-12 14:59:32',0,'中国移动','',NULL,0,0,'方寻',60,'客户介绍',NULL,'13413115046','购买无线路由'),(15,'','2014-09-12 14:00:20',0,'星湖国际公司','有较小机会','2014-09-12 14:03:42',0,1,'张梅',30,'网上','开发成功','12566945684','购买电脑桌'),(16,'','2014-09-12 14:59:13',0,'中国移动','',NULL,0,0,'方寻',60,'客户介绍',NULL,'13413115046','购买无线路由');

#
# Source for table "sal_plan_t"
#

DROP TABLE IF EXISTS `sal_plan_t`;
CREATE TABLE `sal_plan_t` (
  `plan_id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_chc_id` int(11) NOT NULL,
  `plan_date` datetime DEFAULT NULL,
  `plan_result` varchar(255) DEFAULT NULL,
  `plan_todo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

#
# Data for table "sal_plan_t"
#

INSERT INTO `sal_plan_t` VALUES (2,1,'2014-09-11 16:27:07','客户有购买意向','呼唤'),(3,2,'2014-09-11 17:23:46','客户有意向','交流'),(4,2,'2014-09-11 17:23:52',NULL,'谈协议'),(5,5,'2014-09-12 01:34:39',NULL,'交流'),(8,15,'2014-09-12 14:04:45','客户有需求','初步接触客户'),(9,15,'2014-09-12 14:04:56',NULL,'推销产品'),(10,14,'2014-09-12 14:07:06','没有需求','初步接触客户'),(11,1,'2014-09-12 15:05:57','客户确认购买','电话营销');

#
# Source for table "sys_right_t"
#

DROP TABLE IF EXISTS `sys_right_t`;
CREATE TABLE `sys_right_t` (
  `right_id` int(11) NOT NULL AUTO_INCREMENT,
  `right_text` varchar(255) DEFAULT NULL,
  `right_tip` varchar(255) DEFAULT NULL,
  `right_type` varchar(255) DEFAULT NULL,
  `rigth_url` varchar(255) DEFAULT NULL,
  `sys_right_t_right_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`right_id`),
  KEY `FK2BB9A77F4AD88E3B` (`sys_right_t_right_id`),
  CONSTRAINT `FK2BB9A77F4AD88E3B` FOREIGN KEY (`sys_right_t_right_id`) REFERENCES `sys_right_t` (`right_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

#
# Data for table "sys_right_t"
#

INSERT INTO `sys_right_t` VALUES (1,'','营销管理','','',NULL),(3,'','销售机会管理','','chance/list_Chance',1),(4,'','客户管理','','',NULL),(12,'','服务管理','','',NULL),(13,'','统计报表','','',NULL),(14,'','基础数据','','',NULL),(15,'','客户信息管理','','client/list_Chance',4),(27,'','客户流失','','client/list_Lst',4),(29,'','客户开发计划','','chance/list_Plan',1),(30,'','服务创建','','service/open_CreateService',12),(31,'','服务分配','','service/open_AssignService',12),(32,'','服务处理','','service/open_ResolveService',12),(33,'','服务反馈','','service/open_BackeService',12),(34,'','服务归档','','service/open_StoreService',12),(35,'','客户贡献分析','','stat/offer_AnalysisList',13),(36,'','客户构成分析','','stat/form_AnalysisList',13),(38,'','客户服务分析','','stat/service_AnalysisList',13),(39,'','客户流失分析','','stat/lst_AnalysisList',13),(40,'','客户流失分析','','dict/open_CreateDict',14),(41,'','查询产品信息','','dict/open_Product',14),(42,'','查询库存','','dict/open_Stock',14),(45,NULL,'订单管理',NULL,NULL,NULL),(46,'','订单信息管理','','indent/open_CreateIndent',45),(47,NULL,'权限管理',NULL,NULL,NULL),(48,'','系统权限','','right/open_Right',47),(49,'','角色管理','','right/open_Role',47),(50,'','用户角色','','right/open_User',47);

#
# Source for table "sys_role_t"
#

DROP TABLE IF EXISTS `sys_role_t`;
CREATE TABLE `sys_role_t` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_desc` varchar(255) DEFAULT NULL,
  `role_flag` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "sys_role_t"
#

INSERT INTO `sys_role_t` VALUES (1,'','使用中','客户经理'),(4,'','使用中','销售主管'),(5,'','使用中','高管'),(6,'','使用中','系统管理员');

#
# Source for table "sys_role_right_t"
#

DROP TABLE IF EXISTS `sys_role_right_t`;
CREATE TABLE `sys_role_right_t` (
  `rr_right_id` int(11) NOT NULL,
  `rr_role_id` int(11) NOT NULL,
  PRIMARY KEY (`rr_right_id`,`rr_role_id`),
  KEY `FK24DCE87AE4A33B84` (`rr_role_id`),
  KEY `FK24DCE87A5F8864BA` (`rr_right_id`),
  CONSTRAINT `FK24DCE87A5F8864BA` FOREIGN KEY (`rr_right_id`) REFERENCES `sys_right_t` (`right_id`),
  CONSTRAINT `FK24DCE87AE4A33B84` FOREIGN KEY (`rr_role_id`) REFERENCES `sys_role_t` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "sys_role_right_t"
#

INSERT INTO `sys_role_right_t` VALUES (1,1),(1,4),(1,6),(3,1),(3,4),(3,6),(4,1),(4,4),(4,5),(12,4),(13,5),(15,1),(15,4),(27,1),(27,4),(29,1),(29,4),(29,6),(30,1),(30,4),(31,4),(32,1),(33,1),(35,4),(35,5),(36,4),(36,5),(38,4),(38,5),(39,4),(39,5),(47,6),(48,6),(49,6),(50,6);

#
# Source for table "sys_user_t"
#

DROP TABLE IF EXISTS `sys_user_t`;
CREATE TABLE `sys_user_t` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_flag` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `sys_role_t_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK61EA7AD233E34E1` (`sys_role_t_role_id`),
  CONSTRAINT `FK61EA7AD233E34E1` FOREIGN KEY (`sys_role_t_role_id`) REFERENCES `sys_role_t` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Data for table "sys_user_t"
#

INSERT INTO `sys_user_t` VALUES (1,'使用中','杰雄','123456789',5),(3,'使用中','hongxuechao','123456',4),(4,'使用中','xiaoshou','123456',1),(5,'使用中','root','123456',6);

#
# Source for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `post` varchar(255) DEFAULT NULL,
  `sex` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES (1,'胡杰雄','客户经理','男'),(2,'小王','客户经理','男'),(3,'小红','客户经理','男'),(4,'小黑','客户经理','男'),(5,'洪学超','高级客户经理','男'),(6,'吴凯❤','经理助理','男');

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
