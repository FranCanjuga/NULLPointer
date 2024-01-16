
CREATE TABLE IF NOT EXISTS priznanje_donora (
                                              id SERIAL PRIMARY KEY,
                                              priznanje_id INT,
                                              donation_history_id INT,
                                              FOREIGN KEY (priznanje_id) REFERENCES priznanje(priznanje_id),
                                              FOREIGN KEY (donation_history_id) REFERENCES donation_history(donation_history_id)
    );


ALTER TABLE donor
ADD FOREIGN KEY (user_username) REFERENCES users(username);


ALTER TABLE donor DROP COLUMN blood_type;

ALTER TABLE donor ADD COLUMN blood_type INT;




ALTER TABLE donor ADD CONSTRAINT FK_Donor_BloodType
FOREIGN KEY (blood_type)
REFERENCES blood_type(blood_type_id);

