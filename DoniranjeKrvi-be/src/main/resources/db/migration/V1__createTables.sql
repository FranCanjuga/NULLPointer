CREATE TABLE IF NOT EXISTS priznanje (
                                         priznanje_id SERIAL PRIMARY KEY,
                                         name_priznanje VARCHAR(255),
                                        condition INT
    );

CREATE TABLE IF NOT EXISTS roles (
                                     role_id SERIAL PRIMARY KEY,
                                     authority VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS priznanje (
                                         priznanje_id SERIAL PRIMARY KEY,
                                         name_priznanje VARCHAR(255),
    condition INT
    );

CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(20),
    password VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS blood_type (
                                         blood_type_id SERIAL PRIMARY KEY,
                                         type VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS donation_location (
                                                locationID SERIAL PRIMARY KEY,
                                                location_name VARCHAR(255) UNIQUE,
    longitude DOUBLE PRECISION,
    latitude DOUBLE PRECISION
    );

CREATE TABLE IF NOT EXISTS donor (
                                     donor_id SERIAL PRIMARY KEY,
                                     username VARCHAR(255) UNIQUE,
    date_of_birth DATE,
    gender VARCHAR(10),
    blood_type VARCHAR(255),
    location_id INT,
    verified BOOLEAN,
    user_username VARCHAR(255) UNIQUE,
    FOREIGN KEY (location_id) REFERENCES donationLocation(locationID)
    );

CREATE TABLE IF NOT EXISTS appointment (
                                           appointmentID SERIAL PRIMARY KEY,
                                           location_id INT,
                                           critical_action BOOLEAN,
                                           date_and_time TIMESTAMP,
                                           FOREIGN KEY (location_id) REFERENCES donationLocation(locationID)
    );

CREATE TABLE IF NOT EXISTS akcija_krv (
                                          id SERIAL PRIMARY KEY,
                                          blood_type_id INT,
                                          appointment_id INT,
                                          FOREIGN KEY (blood_type_id) REFERENCES bloodType(blood_type_id),
                                          FOREIGN KEY (appointment_id) REFERENCES appointment(appointmentID)
    );



CREATE TABLE IF NOT EXISTS donation_history (
                                                donation_history_id SERIAL PRIMARY KEY,
                                                donor_id INT,
                                                appointment_id INT,
                                                came BOOLEAN,
                                                FOREIGN KEY (donor_id) REFERENCES donor(donor_id),
                                                FOREIGN KEY (appointment_id) REFERENCES appointment(appointmentID)
    );


CREATE TABLE IF NOT EXISTS potvrda (
                                       potvrda_id SERIAL PRIMARY KEY,
                                       name_potvrda VARCHAR(255)
    );
CREATE TABLE IF NOT EXISTS potvrde_donora (
                                              id SERIAL PRIMARY KEY,
                                              potvrda_id INT,
                                              donation_history_id INT,
                                              expiers DATE,
                                              given BOOLEAN,
                                              FOREIGN KEY (potvrda_id) REFERENCES potvrda(potvrda_id),
                                              FOREIGN KEY (donation_history_id) REFERENCES donation_history(donation_history_id)
    );

