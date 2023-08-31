CREATE TABLE IF NOT EXISTS instruments (

		id BIGINT IDENTITY NOT NULL,
		instrument_name VARCHAR(50),
		interest_rate FLOAT,
		start_payment_date TIMESTAMP,
		end_payment_date TIMESTAMP,
		interest_frequency VARCHAR(50),
		principal_frequency VARCHAR(50),
		instrument_type VARCHAR(50),
		PRIMARY KEY (id)

);

CREATE TABLE IF NOT EXISTS positions ( 

		id BIGINT IDENTITY NOT NULL,
		deal_start_date TIMESTAMP, 
		payer_name VARCHAR(50), 
		receiver_name VARCHAR(50), 
		principal REAL,
		position_volume REAL,
		PRIMARY KEY (id), 
		fk_instrument BIGINT, 
		FOREIGN KEY (fk_instrument) REFERENCES instruments(id) ON DELETE CASCADE 

);

CREATE TABLE IF NOT EXISTS transactions ( 

		id BIGINT IDENTITY NOT NULL, 
		amount REAL,
                sign INT,
		transaction_date TIMESTAMP, 
		PRIMARY KEY (id), 
		fk_position BIGINT, 
		FOREIGN KEY (fk_position) REFERENCES positions(id) ON DELETE CASCADE

);