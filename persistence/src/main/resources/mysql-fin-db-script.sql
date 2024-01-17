CREATE SCHEMA IF NOT EXISTS fin;
USE fin;

CREATE TABLE IF NOT EXISTS instrument (

		instrument_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,

		instrument_name VARCHAR(50) NOT NULL,
		interest_rate DOUBLE, 
		start_payment_date TIMESTAMP, 
		end_payment_date TIMESTAMP, 
		interest_frequency VARCHAR(50), 
		principal_frequency VARCHAR(50), 
		instrument_type VARCHAR(50), 

);

CREATE UNIQUE INDEX idx_instr_name ON instrument(instrument_name);
CREATE INDEX idx_instr_type ON instrument(instrument_type);

CREATE TABLE IF NOT EXISTS `position` (
		position_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
		instrument_id BIGINT NOT NULL,

		deal_start_date TIMESTAMP NOT NULL,
		payer_name VARCHAR(50) NOT NULL,
		receiver_name VARCHAR(50) NOT NULL,
		principal DOUBLE NOT NULL,
        position_volume DOUBLE NOT NULL,

		FOREIGN KEY (instrument_id) REFERENCES instrument(instrument_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `transaction` (

		transaction_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
		position_id BIGINT NOT NULL,

		amount DOUBLE NOT NULL,
        sign INT NOT NULL, -- buy(-) or sell(+) trx
		transaction_date TIMESTAMP NOT NULL,

		FOREIGN KEY (position_id) REFERENCES `position`(position_id) ON DELETE CASCADE
);



insert into instrument values('inst1', 0.05, '2010-03-01 16:17:46', '2020-03-01 22:11:22', 'ANNUALLY', 'MONTHLY', 'credit');
insert into instrument values('inst2', 0.02, '2011-03-01 16:17:46', '2021-03-01 22:11:22', 'MONTHLY', null, 'deposit');
insert into instrument values('inst3', 0.01, '2012-03-01 16:17:46', '2022-03-01 22:11:22', 'ANNUALLY', null, 'deposit');
insert into instrument values('inst4', 0.03, '2013-03-01 16:17:46', '2023-03-01 22:11:22', 'MONTHLY', 'MONTHLY', 'credit');
insert into instrument values('inst5', 0.04, '2013-01-01 16:17:46', '2024-03-01 22:11:22', 'ANNUALLY', 'ANNUALLY', 'credit');
insert into instrument values('inst6', null, '2014-03-01 16:17:46', '2024-03-01 22:11:22', null, null, 'share');
insert into instrument values('inst7', null, '2013-03-01 16:17:46', '2023-03-01 22:11:22', null, null, 'share');
insert into instrument values('inst8', 0.1, '2012-03-01 16:17:46', '2012-03-01 22:11:22', 'ANNUALLY', null, 'deposit');
insert into instrument values('inst9', 0.12, '2011-03-01 16:17:46', '2021-03-01 22:11:22', 'MONTHLY', 'MONTHLY', 'credit');
insert into instrument values('inst10', 0.05, '2009-03-01 16:17:46', '2019-03-01 22:11:22', 'ANNUALLY', null, 'deposit');
insert into instrument values('inst11', 0.03, '2008-03-01 16:17:46', '2018-03-01 22:11:22', 'MONTHLY', 'MONTHLY', 'credit');
insert into instrument values('inst12', 0.2, '2007-03-01 16:17:46', '2017-03-01 22:11:22', 'ANNUALLY', 'MONTHLY', 'credit');
insert into instrument values('inst13', 0.3, '2006-03-01 16:17:46', '2016-03-01 22:11:22', 'ANNUALLY', null, 'deposit');
insert into instrument values('inst14', 0.09, '2005-03-01 16:17:46', '2015-03-01 22:11:22', 'MONTHLY', 'ANNUALLY', 'credit');
insert into instrument values('inst15', 0.1, '2004-03-01 16:17:46', '2014-03-01 22:11:22', 'ANNUALLY', 'ANNUALLY', 'credit');
insert into instrument values('inst16', null, '2003-03-01 16:17:46', '2013-03-01 22:11:22', null, null, 'share');
insert into instrument values('inst17', null, '2002-03-01 16:17:46', '2012-03-01 22:11:22', null, null, 'share');
insert into instrument values('inst18', 0.05, '2001-03-01 16:17:46', '2011-03-01 22:11:22', 'MONTHLY', null, 'deposit');
insert into instrument values('inst19', 0.08, '2000-03-01 16:17:46', '2010-03-01 22:11:22', 'MONTHLY', 'ANNUALLY', 'credit');
insert into instrument values('inst20', 0.05, '2009-03-01 16:17:46', '2016-03-01 22:11:22', 'ANNUALLY', null, 'deposit');

insert into position values('2010-03-01 16:17:46', 1, 'prn', 'rrn', null, null);
insert into position values('2011-03-01 16:17:46', 2, 'hgl', 'khy', null, null);
insert into position values('2011-03-01 16:17:46', 2, 'juo', 'dfg', null, null);
insert into position values('2012-03-01 16:17:46', 3, 'gkn', 'cvb', null, null);
insert into position values('2010-03-01 16:17:46', 1, 'vhr', 'oss', null, null);
insert into position values('2013-03-01 16:17:46', 7, 'eor', 'gkd', null, null);
insert into position values('2013-01-01 16:17:46', 5, 'fid', 'tsc', null, null);
insert into position values('2013-01-01 16:17:46', 5, 'fid', 'tsc', null, null);
insert into position values('2013-03-01 16:17:46', 4, 'fid', 'tsc', null, null);
insert into position values('2009-03-01 16:17:46', 10, 'fid', 'tsc', null, null);
insert into position values('2010-03-01 16:17:46', 1, 'prn', 'rrn', null, null);
insert into position values('2011-03-01 16:17:46', 2, 'hgl', 'khy', null, null);
insert into position values('2001-03-01 16:17:46', 18, 'juo', 'dfg', null, null);
insert into position values('2004-03-01 16:17:46', 15, 'gkn', 'cvb', null, null);
insert into position values('2000-03-01 16:17:46', 19, 'vhr', 'oss', null, null);
insert into position values('2009-03-01 16:17:46', 20, 'eor', 'gkd', null, null);
insert into position values('2006-03-01 16:17:46', 13, 'fid', 'tsc', null, null);
insert into position values('2007-03-01 16:17:46', 12, 'fid', 'tsc', null, null);
insert into position values('2005-03-01 16:17:46', 14, 'fid', 'tsc', null, null);
insert into position values('2002-03-01 16:17:46', 17, 'fid', 'tsc', null, null);

insert into `transaction` values(1, 2, 20302, 1,  '2011-03-01 16:17:46');
insert into `transaction` values(2, 3, 2002, 1, '2011-03-01 16:17:46');
insert into `transaction` values(3, 3, 3454, 1, '2011-03-01 16:17:46');
insert into `transaction` values(4, 2, 2434546, -1, '2011-03-01 16:17:46');
insert into `transaction` values(5, 1, 265457, -1, '2010-03-01 16:17:46');
insert into `transaction` values(6, 4, 99686, 1, '2012-03-01 16:17:46');
insert into `transaction` values(7, 8, 75675, -1, '2013-01-01 16:17:46');
insert into `transaction` values(8, 10, 654788, 1, '2009-03-01 16:17:46');
insert into `transaction` values(9, 5, 54645759, -1,  '2010-03-01 16:17:46');
insert into `transaction` values(10, 6, 64578, -1, '2013-03-01 16:17:46');
insert into `transaction` values(11, 2, 20302, 1, '2011-03-01 16:17:46');
insert into `transaction` values(12, 14, 2002, 1, '2004-03-01 16:17:46');
insert into `transaction` values(13, 13, 3454, 1, '2001-03-01 16:17:46');
insert into `transaction` values(14, 11, 2434546, 1, '2010-03-01 16:17:46');
insert into `transaction` values(15, 19, 100, 1, '2005-03-01 16:17:46');
insert into `transaction` values(16, 19, 100, -1, '2005-06-01 16:17:46');
insert into `transaction` values(17, 19, 100, 1, '2005-09-01 16:17:46');
insert into `transaction` values(18, 19, 100, -1, '2005-12-01 16:17:46');
insert into `transaction` values(19, 19, 100, 1, '2006-03-01 16:17:46');
insert into `transaction` values(20, 19, 100, 1, '2006-06-01 16:17:46');
insert into `transaction` values(21, 16, 250, 1, '2009-03-01 16:17:46');
insert into `transaction` values(22, 16,  250, -1, '2009-06-01 16:17:46');
insert into `transaction` values(23, 16,  250, 1, '2009-09-01 16:17:46');
insert into `transaction` values(24, 16, 250, -1, '2009-12-01 16:17:46');
insert into `transaction` values(25, 16,  250, 1, '2010-01-01 16:17:46');
insert into `transaction` values(26, 16, 250, 1, '2010-04-01 16:17:46');

