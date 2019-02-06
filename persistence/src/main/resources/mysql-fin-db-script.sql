CREATE TABLE IF NOT EXISTS instruments ( 

		id BIGINT AUTO_INCREMENT NOT NULL,
		instrument_name VARCHAR(50), 
		interest_rate DOUBLE, 
		start_payment_date TIMESTAMP, 
		end_payment_date TIMESTAMP, 
		interest_frequency VARCHAR(50), 
		principal_frequency VARCHAR(50), 
		instrument_type VARCHAR(50), 
		PRIMARY KEY (id) 

);

CREATE TABLE IF NOT EXISTS positions ( 

		id BIGINT AUTO_INCREMENT NOT NULL,
		deal_start_date TIMESTAMP, 
		payer_name VARCHAR(50), 
		receiver_name VARCHAR(50), 
		principal DOUBLE, 
                position_volume DOUBLE,
		PRIMARY KEY (id), 
		fk_instrument BIGINT, 
		FOREIGN KEY (fk_instrument) REFERENCES instruments(id) ON DELETE CASCADE 

);

CREATE TABLE IF NOT EXISTS transactions ( 

		id BIGINT AUTO_INCREMENT NOT NULL, 
		amount DOUBLE,
                sign INT,
		transaction_date TIMESTAMP, 
		PRIMARY KEY (id), 
		fk_position BIGINT, 
		FOREIGN KEY (fk_position) REFERENCES positions(id) ON DELETE CASCADE

);



insert into instruments values(1, 'inst1', 0.05, '2010-03-01 16:17:46', '2020-03-01 22:11:22', 'ANNUALLY', 'MONTHLY', 'credit');
insert into instruments values(2, 'inst2', 0.02, '2011-03-01 16:17:46', '2021-03-01 22:11:22', 'MONTHLY', null, 'deposit');
insert into instruments values(3, 'inst3', 0.01, '2012-03-01 16:17:46', '2022-03-01 22:11:22', 'ANNUALLY', null, 'deposit');
insert into instruments values(4, 'inst4', 0.03, '2013-03-01 16:17:46', '2023-03-01 22:11:22', 'MONTHLY', 'MONTHLY', 'credit');
insert into instruments values(5, 'inst5', 0.04, '2013-01-01 16:17:46', '2024-03-01 22:11:22', 'ANNUALLY', 'ANNUALLY', 'credit');
insert into instruments values(6, 'inst6', null, '2014-03-01 16:17:46', '2024-03-01 22:11:22', null, null, 'share');
insert into instruments values(7, 'inst7', null, '2013-03-01 16:17:46', '2023-03-01 22:11:22', null, null, 'share');
insert into instruments values(8, 'inst8', 0.1, '2012-03-01 16:17:46', '2012-03-01 22:11:22', 'ANNUALLY', null, 'deposit');
insert into instruments values(9, 'inst9', 0.12, '2011-03-01 16:17:46', '2021-03-01 22:11:22', 'MONTHLY', 'MONTHLY', 'credit');
insert into instruments values(10, 'inst10', 0.05, '2009-03-01 16:17:46', '2019-03-01 22:11:22', 'ANNUALLY', null, 'deposit');
insert into instruments values(11, 'inst11', 0.03, '2008-03-01 16:17:46', '2018-03-01 22:11:22', 'MONTHLY', 'MONTHLY', 'credit');
insert into instruments values(12, 'inst12', 0.2, '2007-03-01 16:17:46', '2017-03-01 22:11:22', 'ANNUALLY', 'MONTHLY', 'credit');
insert into instruments values(13, 'inst13', 0.3, '2006-03-01 16:17:46', '2016-03-01 22:11:22', 'ANNUALLY', null, 'deposit');
insert into instruments values(14, 'inst14', 0.09, '2005-03-01 16:17:46', '2015-03-01 22:11:22', 'MONTHLY', 'ANNUALLY', 'credit');
insert into instruments values(15, 'inst15', 0.1, '2004-03-01 16:17:46', '2014-03-01 22:11:22', 'ANNUALLY', 'ANNUALLY', 'credit');
insert into instruments values(16, 'inst16', null, '2003-03-01 16:17:46', '2013-03-01 22:11:22', null, null, 'share');
insert into instruments values(17, 'inst17', null, '2002-03-01 16:17:46', '2012-03-01 22:11:22', null, null, 'share');
insert into instruments values(18, 'inst18', 0.05, '2001-03-01 16:17:46', '2011-03-01 22:11:22', 'MONTHLY', null, 'deposit');
insert into instruments values(19, 'inst19', 0.08, '2000-03-01 16:17:46', '2010-03-01 22:11:22', 'MONTHLY', 'ANNUALLY', 'credit');
insert into instruments values(20, 'inst20', 0.05, '2009-03-01 16:17:46', '2016-03-01 22:11:22', 'ANNUALLY', null, 'deposit');

insert into positions values(1, '2010-03-01 16:17:46', 'prn', 'rrn', null, null, (select id from instruments where id=1));
insert into positions values(2, '2011-03-01 16:17:46', 'hgl', 'khy', null, null, (select id from instruments where id=2));
insert into positions values(3, '2011-03-01 16:17:46', 'juo', 'dfg', null, null, (select id from instruments where id=2));
insert into positions values(4, '2012-03-01 16:17:46', 'gkn', 'cvb', null, null, (select id from instruments where id=3));
insert into positions values(5, '2010-03-01 16:17:46', 'vhr', 'oss', null, null, (select id from instruments where id=1));
insert into positions values(6, '2013-03-01 16:17:46', 'eor', 'gkd', null, null, (select id from instruments where id=7));
insert into positions values(7, '2013-01-01 16:17:46', 'fid', 'tsc', null, null, (select id from instruments where id=5));
insert into positions values(8, '2013-01-01 16:17:46', 'fid', 'tsc', null, null, (select id from instruments where id=5));
insert into positions values(9, '2013-03-01 16:17:46', 'fid', 'tsc', null, null, (select id from instruments where id=4));
insert into positions values(10, '2009-03-01 16:17:46', 'fid', 'tsc', null, null, (select id from instruments where id=10));
insert into positions values(11, '2010-03-01 16:17:46', 'prn', 'rrn', null, null,  (select id from instruments where id=1));
insert into positions values(12, '2011-03-01 16:17:46', 'hgl', 'khy', null, null, (select id from instruments where id=2));
insert into positions values(13, '2001-03-01 16:17:46', 'juo', 'dfg', null, null, (select id from instruments where id=18));
insert into positions values(14, '2004-03-01 16:17:46', 'gkn', 'cvb', null, null, (select id from instruments where id=15));
insert into positions values(15, '2000-03-01 16:17:46', 'vhr', 'oss', null, null, (select id from instruments where id=19));
insert into positions values(16, '2009-03-01 16:17:46', 'eor', 'gkd', null, null, (select id from instruments where id=20));
insert into positions values(17, '2006-03-01 16:17:46', 'fid', 'tsc', null, null, (select id from instruments where id=13));
insert into positions values(18, '2007-03-01 16:17:46', 'fid', 'tsc', null, null, (select id from instruments where id=12));
insert into positions values(19, '2005-03-01 16:17:46', 'fid', 'tsc', null, null, (select id from instruments where id=14));
insert into positions values(20, '2002-03-01 16:17:46', 'fid', 'tsc', null, null, (select id from instruments where id=17));

insert into transactions values(1, 20302, 1,  '2011-03-01 16:17:46', (select id from positions where id = 2));
insert into transactions values(2, 2002, 1, '2011-03-01 16:17:46', (select id from positions where id = 3));
insert into transactions values(3, 3454, 1, '2011-03-01 16:17:46', (select id from positions where id = 3));
insert into transactions values(4, 2434546, -1, '2011-03-01 16:17:46', (select id from positions where id = 2));
insert into transactions values(5, 265457, -1, '2010-03-01 16:17:46', (select id from positions where id = 1));
insert into transactions values(6, 99686, 1, '2012-03-01 16:17:46', (select id from positions where id = 4));
insert into transactions values(7, 75675, -1, '2013-01-01 16:17:46', (select id from positions where id = 8));
insert into transactions values(8, 654788, 1, '2009-03-01 16:17:46', (select id from positions where id = 10));
insert into transactions values(9, 54645759, -1,  '2010-03-01 16:17:46', (select id from positions where id = 5));
insert into transactions values(10, 64578, -1, '2013-03-01 16:17:46', (select id from positions where id = 6));
insert into transactions values(11, 20302, 1, '2011-03-01 16:17:46', (select id from positions where id = 2));
insert into transactions values(12, 2002, 1, '2004-03-01 16:17:46', (select id from positions where id = 14));
insert into transactions values(13, 3454, 1, '2001-03-01 16:17:46', (select id from positions where id = 13));
insert into transactions values(14, 2434546, 1, '2010-03-01 16:17:46', (select id from positions where id = 11));
insert into transactions values(15, 100, 1, '2005-03-01 16:17:46', (select id from positions where id = 19));
insert into transactions values(16, 100, -1, '2005-06-01 16:17:46', (select id from positions where id = 19));
insert into transactions values(17, 100, 1, '2005-09-01 16:17:46', (select id from positions where id = 19));
insert into transactions values(18, 100, -1, '2005-12-01 16:17:46', (select id from positions where id = 19));
insert into transactions values(19, 100, 1, '2006-03-01 16:17:46', (select id from positions where id = 19));
insert into transactions values(20, 100, 1, '2006-06-01 16:17:46', (select id from positions where id = 19));
insert into transactions values(21, 250, 1, '2009-03-01 16:17:46', (select id from positions where id = 16));
insert into transactions values(22, 250, -1, '2009-06-01 16:17:46', (select id from positions where id = 16));
insert into transactions values(23, 250, 1, '2009-09-01 16:17:46', (select id from positions where id = 16));
insert into transactions values(24, 250, -1, '2009-12-01 16:17:46', (select id from positions where id = 16));
insert into transactions values(25, 250, 1, '2010-01-01 16:17:46', (select id from positions where id = 16));
insert into transactions values(26, 250, 1, '2010-04-01 16:17:46', (select id from positions where id = 16));

