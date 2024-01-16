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
                                                location_id SERIAL PRIMARY KEY,
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
    location_id BIGINT,
    verified BOOLEAN,
    user_username VARCHAR(255),

    FOREIGN KEY (location_id) REFERENCES donation_location(location_id)
    );

CREATE TABLE IF NOT EXISTS appointment (
                                           appointment_id SERIAL PRIMARY KEY,
                                           location INT,
                                           critical_action BOOLEAN,
                                           date_and_time TIMESTAMP,
                                           FOREIGN KEY (location) REFERENCES donation_location(location_id)
    );

CREATE TABLE IF NOT EXISTS akcija_krv (
                                          id SERIAL PRIMARY KEY,
                                          bloodType INT,
                                          appointment INT,
                                          FOREIGN KEY (bloodType) REFERENCES blood_type(blood_type_id),
                                          FOREIGN KEY (appointment) REFERENCES appointment(appointment_id)
    );



CREATE TABLE IF NOT EXISTS donation_history (
                                                donation_history_id SERIAL PRIMARY KEY,
                                                donor INT,
                                                appointment_id  BIGINT,
                                                came BOOLEAN,
                                                FOREIGN KEY (donor) REFERENCES donor(donor_id),
                                                FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id)
    );


CREATE TABLE IF NOT EXISTS potvrda (
                                       potvrda_id SERIAL PRIMARY KEY,
                                       name_potvrda VARCHAR(255)
    );
CREATE TABLE IF NOT EXISTS potvrde_donora (
                                              id SERIAL PRIMARY KEY,
                                              potvrda INT,
                                              donation_history INT,
                                              expiers DATE,
                                              given BOOLEAN,
                                              FOREIGN KEY (potvrda) REFERENCES potvrda(potvrda_id),
                                              FOREIGN KEY (donation_history) REFERENCES donation_history(donation_history_id)
    );

