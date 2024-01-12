
CREATE TABLE IF NOT EXISTS priznanje_donora (
                                              id SERIAL PRIMARY KEY,
                                              priznanje_id INT,
                                              donationHistory_id INT,
                                              FOREIGN KEY (priznanje_id) REFERENCES priznanje(priznanje_id),
                                              FOREIGN KEY (donation_history_id) REFERENCES donation_history(donation_history_id)
    );


ALTER TABLE donor
ADD FOREIGN KEY (user_username) REFERENCES users(username);


ALTER TABLE Donor ADD COLUMN blood_type_id INT;

ALTER TABLE Donor DROP COLUMN blood_type;


ALTER TABLE Donor ADD CONSTRAINT FK_Donor_BloodType
FOREIGN KEY (blood_type_id)
REFERENCES blood_type(blood_type_id);

