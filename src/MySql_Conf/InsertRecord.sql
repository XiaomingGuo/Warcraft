-- Create user_info records
INSERT INTO `earthquake`.`user_info` (`name`,`password`,`department`,`permission`) VALUES ("wallace.guo","123456","���²�",0);
INSERT INTO `earthquake`.`user_info` (`name`,`password`,`department`,`permission`) VALUES ('������', 'abc@123', '����һ��',0);
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("�ľ�");
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("����");
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("�Ĳ�");
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("IT����");
INSERT INTO `earthquake`.`product_type` (`name`) VALUES ("����");
INSERT INTO `earthquake`.`product_info` (`name`,`product_type`) VALUES ("���Ա�","�ľ�");
INSERT INTO `earthquake`.`product_info` (`name`,`product_type`) VALUES ("���Ա�о","�ľ�");
INSERT INTO `earthquake`.`product_info` (`name`,`product_type`) VALUES ("����","����");
INSERT INTO `earthquake`.`product_info` (`name`,`product_type`) VALUES ("����","����");
INSERT INTO `earthquake`.`material_record` (`proposer`,`material_name`,`QTY`,`isApprove`) VALUES ("wallace.guo","���Ա�",1,0);
INSERT INTO `earthquake`.`material_record` (`proposer`,`material_name`,`QTY`,`isApprove`) VALUES ("������","����",1,0);
INSERT INTO `earthquake`.`material_record` (`proposer`,`material_name`,`QTY`,`isApprove`) VALUES ("������","���Ա�о",1,0);