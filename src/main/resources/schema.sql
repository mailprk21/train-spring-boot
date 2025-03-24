CREATE TABLE section ( 
   section_key INTEGER PRIMARY KEY AUTO_INCREMENT,
   section_name ENUM('A','B') NOT NULL, 
   start_seat INTEGER NOT NULL, 
   end_seat INTEGER NOT NULL
);

CREATE TABLE train ( 
   train_key INTEGER PRIMARY KEY AUTO_INCREMENT,
   train_no INTEGER NOT NULL, 
   train_date DATE NOT NULL,
   origin VARCHAR(50) NOT NULL, 
   destination VARCHAR(50) NOT NULL
);

CREATE TABLE fare ( 
   fare_key INTEGER PRIMARY KEY AUTO_INCREMENT,
   train_key INTEGER NOT NULL, 
   section_key INTEGER NOT NULL, 
   fare NUMERIC(10,2) NOT NULL
);

ALTER TABLE fare ADD FOREIGN KEY (train_key) REFERENCES train(train_key);

ALTER TABLE fare ADD FOREIGN KEY (section_key) REFERENCES section(section_key);

CREATE TABLE ticket ( 
   ticket_key INTEGER PRIMARY KEY AUTO_INCREMENT,
   train_key INTEGER NOT NULL,
   section_key INTEGER NOT NULL,
   status ENUM('Confirmed','Cancelled') NOT NULL,
   paid_amount NUMERIC(10,2) NOT NULL, 
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   email VARCHAR(50) NOT NULL,
   issued_date DATE NOT NULL
);

ALTER TABLE ticket ADD FOREIGN KEY (train_key) REFERENCES train(train_key);

CREATE TABLE seat ( 
   seat_key INTEGER PRIMARY KEY AUTO_INCREMENT,
   train_key INTEGER NOT NULL, 
   section_key INTEGER NOT NULL, 
   ticket_key INTEGER,
   seat_number INTEGER NOT NULL
);

ALTER TABLE seat ADD FOREIGN KEY (train_key) REFERENCES train(train_key);

ALTER TABLE seat ADD FOREIGN KEY (section_key) REFERENCES section(section_key);

ALTER TABLE seat ADD FOREIGN KEY (ticket_key) REFERENCES ticket(ticket_key);

