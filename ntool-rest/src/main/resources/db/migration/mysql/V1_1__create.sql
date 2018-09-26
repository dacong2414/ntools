
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `nuls_btc`;
CREATE TABLE nuls_btc(
  rec_id INT NOT NULL  AUTO_INCREMENT,
  price VARCHAR(255) NOT NULL ,
  amount VARCHAR(255) NOT NULL ,
  last_update_id VARCHAR(255) NOT NULL,
  bids_or_ask VARCHAR(255) NOT NULL,
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_mod datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`rec_id`)
) ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `nuls_eth`;
CREATE TABLE nuls_eth(
  rec_id INT NOT NULL   AUTO_INCREMENT,
  price VARCHAR(255) NOT NULL ,
  amount VARCHAR(255) NOT NULL ,
  last_update_id VARCHAR(255) NOT NULL,
  bids_or_ask VARCHAR(255) NOT NULL,
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_mod datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`rec_id`)
) ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `nuls_usdt`;
CREATE TABLE nuls_usdt(
  rec_id INT NOT NULL   AUTO_INCREMENT,
  price VARCHAR(255) NOT NULL ,
  amount VARCHAR(255) NOT NULL ,
  last_update_id VARCHAR(255) NOT NULL,
  bids_or_ask VARCHAR(255) NOT NULL,
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_mod datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`rec_id`)
) ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `btc_usdt`;
CREATE TABLE btc_usdt(
  rec_id INT NOT NULL  AUTO_INCREMENT,
  price VARCHAR(255) NOT NULL ,
  amount VARCHAR(255) NOT NULL ,
  last_update_id VARCHAR(255) NOT NULL,
  bids_or_ask VARCHAR(255) NOT NULL,
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_mod datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`rec_id`)
) ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `eth_usdt`;
CREATE TABLE eth_usdt(
  rec_id INT NOT NULL   AUTO_INCREMENT,
  price VARCHAR(255) NOT NULL ,
  amount VARCHAR(255) NOT NULL ,
  last_update_id VARCHAR(255) NOT NULL,
  bids_or_ask VARCHAR(255) NOT NULL,
  gmt_create datetime DEFAULT NULL COMMENT '创建时间',
  gmt_mod datetime DEFAULT NULL  COMMENT '修改时间',
  PRIMARY KEY (`rec_id`)
) ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
