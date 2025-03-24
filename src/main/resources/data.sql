INSERT INTO section ( section_key, section_name,start_seat,end_seat) VALUES (1,'A', 1,5);
INSERT INTO section ( section_key, section_name,start_seat,end_seat) VALUES (2,'B', 5,10);


INSERT INTO train (train_key, train_no,train_date, origin,destination)
VALUES ( 1, 100, current_date, 'LONDON','PARIS');

INSERT INTO fare (fare_key,train_key, section_key, fare)
VALUES (1, (SELECT train_key FROM train WHERE train_no=100),
        (SELECT section_key FROM section WHERE section_name='A'), 100.0);

INSERT INTO fare (fare_key, train_key, section_key, fare)
VALUES (2, (SELECT train_key FROM TRAIN WHERE train_no=100),
        (SELECT section_key FROM section WHERE section_name='B'), 200.0);
commit;