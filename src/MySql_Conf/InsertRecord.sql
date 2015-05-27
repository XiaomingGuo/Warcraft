-- Create user_info records
INSERT INTO `earthquake`.`user_info` (`name`,`password`,`department`,`permission`) VALUES ("wallace.guo","123456","人事部",0);
INSERT INTO `earthquake`.`user_info` (`name`,`password`,`department`,`permission`) VALUES ('郭晓明', 'abc@123', '生产一部',0);
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("文具");
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("物料");
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("耗材");
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("IT附件");
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("其他");
INSERT INTO `earthquake`.`product_info` (`name`,`product_type`) VALUES ("中性笔","文具");
INSERT INTO `earthquake`.`product_info` (`name`,`product_type`) VALUES ("中性笔芯","文具");
INSERT INTO `earthquake`.`product_info` (`name`,`product_type`) VALUES ("铁块","物料");
INSERT INTO `earthquake`.`product_info` (`name`,`product_type`) VALUES ("焊锡","物料");
INSERT INTO `earthquake`.`material_record` (`proposer`,`material_name`,`QTY`,`isApprove`) VALUES ("wallace.guo","中性笔",1,0);
INSERT INTO `earthquake`.`material_record` (`proposer`,`material_name`,`QTY`,`isApprove`) VALUES ("郭晓明","铁块",1,0);
INSERT INTO `earthquake`.`material_record` (`proposer`,`material_name`,`QTY`,`isApprove`) VALUES ("郭晓明","中性笔芯",1,0);